package miniProject;

import java.util.*;

import alumni.Alumni_Profile;

import student.Student;

//Message Node Class

class Message_node {

	String content;

	String student;

	String alumni;

	Message_node next;

	Message_node(String content, String student, String alumni) {

		this.content = content;

		this.student = student;

		this.alumni = alumni;

		this.next = null;

	}

}

//Message Methods Class

class Message {

	boolean value;

	Scanner sc = new Scanner(System.in);

	private Message_node head;

	Message() {

		head = null;

	}

	Message_node ptr;

//Method To Check If The String Only Contains Alphabets

	public boolean onlyAlphabets(String str) {

		int n = str.length();

		for (int i = 0; i < n; i++) {

			if (!Character.isAlphabetic(str.charAt(i))) {

				return false;

			}

		}

		return true;

	}

//Method To Display Received and Sent Messages

	void display_msg(String student_name, String alumni_name) {

		ptr = head;

		int flag = 0;

		while (ptr != null) {

			if (ptr.student.equals(student_name) && ptr.alumni.equals(alumni_name)) {

				System.out.println(ptr.content);

				flag = 1;

			}

			ptr = ptr.next;

		}

		if (flag == 0) {

			System.out.println("\n\nNO CONVERSATIONS YET!!");

		}

	}

//Method To Send Message to Alumni

	void send_student(String student_name, String alumni_name) {

		ptr = head;

		display_msg(student_name, alumni_name);

		System.out.print("\nMESSAGE : ");

		String message = student_name + " : " + sc.nextLine() + "\n";

		Message_node temp = new Message_node(message, student_name, alumni_name);

		if (head == null) {

			head = temp;

		} else {

			ptr = head;

			while (ptr.next != null) {

				ptr = ptr.next;

			}

			ptr.next = temp;

			temp.next = null;

		}

	}

//Method to send Message to Student

	void send_alumni(String student_name, String alumni_name) {

		ptr = head;

		display_msg(student_name, alumni_name);

		System.out.print("\nMESSAGE : ");

		String message = alumni_name + " : " + sc.nextLine() + "\n";

		Message_node temp = new Message_node(message, student_name, alumni_name);

		if (head == null) {

			head = temp;

		} else {

			ptr = head;

			while (ptr.next != null) {

				ptr = ptr.next;

			}

			ptr.next = temp;

			temp.next = null;

		}

	}

}

//Graph Class

class Graph<T> {

//We use Hashmap to store the edges in the graph

	private Map<T, List<T>> map = new HashMap<>();
	private Map<T, List<T>> map1 = new HashMap<>();

//This function adds a new vertex to the graph

	public void addVertex(T s) {

		map1.put(s, new LinkedList<T>());

	}

	public void addVertex1(T s) {

		map.put(s, new LinkedList<T>());

	}

	String rejected_ids = "";

	public int send_notification(T student_id, T alumni_id) {

		if (!map1.containsKey(student_id)) {
			addVertex(student_id);
		}
		if (!map1.containsKey(alumni_id)) {
			addVertex(alumni_id);
		}

		List<T> studentList = map1.get(alumni_id);

		if (studentList == null) {
			studentList = new ArrayList<>();
			map1.put(alumni_id, studentList);
		}

		for (T w : studentList) {
			if (w.equals(student_id)) {
				System.out.println("\n\nPending request!!");
				return 1;
			}
		}

		String[] split_rejected_ids = rejected_ids.split(" ");
		for (String rejectedIdstr : split_rejected_ids) {

			if (rejectedIdstr.equals(student_id)) {
				System.out.println("\n\nPending request!!");
				return 1;
			}

		}

		studentList.add(student_id);

		return 0;

	}

	public String show_notification(T alumni_id) {
		String ids = "";

		if (map1.containsKey(alumni_id)) {
			List<T> studentList = map1.get(alumni_id);
			if (studentList != null && !studentList.isEmpty()) {
				for (T studentId : studentList) {
					ids += studentId.toString() + " ";
				}
			}
		}

		ids = ids.trim();

		return ids;
	}

//This function adds the edge between source to destination

	public void addEdge(T source, T destination) {

		if (!map.containsKey(source))

			addVertex1(source);

		if (!map.containsKey(destination))

			addVertex1(destination);

		for (T w : map.get(source)) {

			if (w == destination)

			{

				System.out.println("\n\nYOU BOTH ARE ALREADY A CONNECTION!!");

				return;

			}

		}

		map.get(source).add(destination);

		map.get(destination).add(source);

	}

	public void reject_request(String ids, T alumni_id) {

		if (ids.contains(",")) {

			String[] split_connect_ids = ids.split(",");

			for (String studentIdstr : split_connect_ids) {
				studentIdstr = studentIdstr.trim();

				if (!studentIdstr.isEmpty() && studentIdstr.matches("\\d+")) {
					Integer studentId = Integer.parseInt(studentIdstr);

					if (map1.containsKey(alumni_id)) {
						List<T> studentList = map1.get(alumni_id);
						studentList.remove((T) studentId);
					}

					rejected_ids += studentIdstr + " ";
					System.out.println("Student ID " + studentId + " rejected");
				}

				else {
					System.out.println("\n\nInvalid Unique ID");
				}
			}
		}

		else {

			ids = ids.trim();

			if (!ids.isEmpty() && ids.matches("\\d+")) {

				Integer studentId = Integer.parseInt(ids);

				if (map1.containsKey(alumni_id)) {
					List<T> studentList = map1.get(alumni_id);
					studentList.remove((T) studentId);
				}

				rejected_ids += ids + " ";
				System.out.println("Student ID " + studentId + " rejected");
			}

			else {
				System.out.println("\n\nInvalid Unique ID");
			}
		}
	}

	public void accept_request(String ids, T alumni_id) {

		if (ids.contains(",")) {

			String[] split_connect_ids = ids.split(",");

			for (String studentIdstr : split_connect_ids) {

				studentIdstr = studentIdstr.trim();

				if (!studentIdstr.isEmpty() && studentIdstr.matches("\\d+")) {

					int studentId = Integer.parseInt(studentIdstr);
					T studentIdT = (T) Integer.valueOf(studentId);
					addEdge(alumni_id, studentIdT);

				} else {

					System.out.println("Invalid ID: " + studentIdstr);

				}

			}

		} else {

			ids = ids.trim();

			if (!ids.isEmpty() && ids.matches("\\d+")) {

				int studentId = Integer.parseInt(ids);
				T studentIdT = (T) Integer.valueOf(studentId);
				addEdge(alumni_id, studentIdT);

			} else {

				System.out.println("Invalid ID: " + ids);

			}

		}

		if (ids.contains(",")) {

			String[] split_connect_ids = ids.split(",");

			for (String studentIdstr : split_connect_ids) {

				studentIdstr = studentIdstr.trim();

				if (!studentIdstr.isEmpty() && studentIdstr.matches("\\d+")) {

					Integer studentId = Integer.parseInt(studentIdstr);

					if (map1.containsKey(alumni_id)) {
						List<T> studentList = map1.get(alumni_id);
						studentList.remove((T) studentId);
					}

					rejected_ids += studentIdstr + " ";
					System.out.println("Student ID " + studentId + " accepted");
				}

				else {
					System.out.println("\n\nInvalid Unique ID");
				}
			}
		} else {

			ids = ids.trim();

			if (!ids.isEmpty() && ids.matches("\\d+")) {
				Integer studentId = Integer.parseInt(ids);

				if (map1.containsKey(alumni_id)) {
					List<T> studentList = map1.get(alumni_id);
					studentList.remove((T) studentId);
				}

				rejected_ids += ids + " ";
				System.out.println("Student ID " + studentId + " accepted");
			}

			else {
				System.out.println("\n\nInvalid Unique ID");
			}
		}

		System.out.println("\n\nCONNECTION ESTABLISHED SUCCESSFULLY!!");

	}

// Prints the adjancency list of each vertex.

	public String display(T id) {

		String str = "";

		try {
			if (map.containsKey(id)) {

				for (T w : map.get(id)) {

					str += (w.toString() + " ");

				}

			}
		}

		catch (Exception e) {
			return str;
		}

		return str;

	}

}

//MAIN CLASS

public class Main {

	public static void main(String args[]) {

		Graph<Integer> g = new Graph<Integer>();

		Student obj = new Student();

		Alumni_Profile obj1 = new Alumni_Profile();

		Message mess = new Message();

		Scanner sc = new Scanner(System.in);

		System.out.println("\n\n\t\t\t\t\t\t\t -----------------------------------------------------\t\t\t\t");

		System.out.println("\t\t\t\t\t\t\t            STUDENT ALUMNI NETWORKING PLATFORM      \t\t\t\t");

		System.out.println("\t\t\t\t\t\t\t -----------------------------------------------------\t\t\t\t");

		System.out.println("\n\n\t\t\t\t\t\t     ____________________||PROJECT IS MADE BY||____________________\t\t\t");

		System.out.println(

				"\n\n\n\t\t\t\t\t\t\t\t\t  TANUSHREE KADUS 530\n\n\t\t\t\t\t\t\t\t\t  SRUSHTI KAGE 531\n\n\t\t\t\t\t\t\t\t\t  RISHITA KHANDAGALE 542\n\n\n\n\n\n");
		System.out.println("Press Enter to Continue!!");

		try {

			System.in.read();

		} catch (Exception e) {

		}

		while (true) {

			System.out.println(

					"\n\n\n\n\n\t\t\t\t\t\t************************ WELCOME TO ALUMNI CONNECT ************************\n\n");

			System.out.println("\n\n\n1) LOGIN\n2) SIGN IN\n3) EXIT");

			int ch;

			try {

				System.out.print("\nPlease Enter Appropriate Option : ");

				ch = sc.nextInt();

				sc.nextLine();

			} catch (Exception e) {

				System.out.println("\n\nEnter Digits ONLY!!");

				System.out.print("\nRe-Enter : ");

				sc.nextLine();

				ch = sc.nextInt();

				sc.nextLine();

			}

			switch (ch) {

			case 1:

				System.out.println("\n\nWho Are You?\n\n1) STUDENT\n2) ALUMNI");

				int num;

				try {

					System.out.print("\nPlease Enter Appropriate Option : ");

					num = sc.nextInt();

					sc.nextLine();

				}

				catch (Exception e) {

					System.out.println("\n\nEnter Digits ONLY!!");

					System.out.print("\nRe-Enter : ");

					sc.nextLine();

					num = sc.nextInt();

					sc.nextLine();

				}

				if (num == 1) {

					int student_id = obj.log_in(); // Student's id no

					if (student_id != 0) {

						System.out.println("\n\nSUCCESSFULLY LOGGED IN!!");

						while (true) {

							System.out.println(

									"\n\n\n\n\n\t\t\t\t\t\t\t\t                STUDENT'S PAGE                        \t\t\t\t");

							System.out.println("\t\t\t\t\t\t\t\t     -----------------------------------\t\t\t\t\n\n");

							System.out

									.println(

											"\n\n1) SEARCH FOR ALUMNI\n2) SEE YOUR CONNECTIONS\n3) MESSAGES\n4) UPDATE YOUR PROFILE\n5) VIEW PROFILE\n6) LOG OUT");

							System.out.print("\nPlease Enter Appropriate Option : ");

							int ch1 = sc.nextInt();

							if (ch1 == 6) {

								break;

							}

							switch (ch1) {

							case 1:

								int alumni_id = obj1.show_student(); // Alumni's id no

								if (alumni_id == 0) {

									break;

								}

								if (g.send_notification(student_id, alumni_id) == 0) {
									System.out.println("\nRequest Sent!!");
								}

								String op;

								do {

									System.out.print("\n\nWant to make more Connections? (YES/NO) : ");

									String op1 = sc.next();
									op = op1.toUpperCase();

									alumni_id = obj1.connect(op);

									if (alumni_id == 0) {
										break;
									}

									if (alumni_id != 0) {

										if (g.send_notification(student_id, alumni_id) == 0) {
											System.out.println("\nRequest Sent!!");
										}

									}

								} while (op.equals("YES"));

								break;

							case 2:

								String ans = g.display(student_id);

								if (ans == "") {

									System.out.println("\n\nYOU HAVE NO CONNECTIONS!!");

									break;

								}

								System.out.println(
										"\n\n\n\n\n\t\t\t\t\t\t\t\t                YOUR CONNECTIONS                        \t\t\t\t");

								System.out.println(
										"\t\t\t\t\t\t\t\t     ___________________________________\t\t\t\t\n\n");

								obj1.display_alumni(ans);

								break;

							case 3:

								int ch2 = obj.student_messages(); // Return choice

								switch (ch2) {

								case 1:

									String ans1 = g.display(student_id);

									if (ans1 == "") {

										System.out.println("\n\nYOU HAVE NO CONNECTIONS!!");

										break;

									}

									System.out.println(
											"\n\n\n\n\n\t\t\t\t\t\t\t\t                YOUR CONNECTIONS                        \t\t\t\t");

									System.out.println(
											"\t\t\t\t\t\t\t\t     ___________________________________\t\t\t\t\n\n");

									obj1.display_alumni(ans1);

									System.out.print("\n\nEnter Unique Identity No. of Alumni to Message : ");

									int alumni = sc.nextInt();

									int c = obj1.check(alumni, ans1);

									if (c == 0) {

										break;

									}

									String std_name = obj.student_name(student_id);

									String alumni_name = obj1.alumni_name(alumni);

									String a = alumni_name.toUpperCase();

									System.out.println("\n\n\n\n\n\t\t\t\t\t\t\t\t                " + a
											+ "                        \t\t\t\t");

									System.out.println(
											"\t\t\t\t\t\t\t\t       _____________________________\t\t\t\t\n\n");

									mess.send_student(std_name, alumni_name);

									break;

								case 2:

									break;

								}

								break;

							case 4:

								obj.update(student_id);

								break;

							case 5:

								obj.view_profile(student_id);

								break;

							default:

								System.out.println("\n\nEnter a Valid Option!");

							}

						}

					}

				}

				else if (num == 2) {

					int alumni_id = obj1.login();

					if (alumni_id != 0) {

						System.out.println("\n\nSUCCESSFULLY LOGGED IN!!");

						while (true) {

							System.out.println(

									"\n\n\n\n\n\t\t\t\t\t\t\t\t                ALUMNI'S PAGE                        \t\t\t\t");

							System.out.println("\t\t\t\t\t\t\t\t     -----------------------------------\t\t\t\t\n\n");

							System.out.println(

									"\n\n1) UPDATE YOUR PROFILE\n2) VIEW PROFILE\n3) SEE YOUR CONNECTIONS\n4) MESSAGES\n5) NOTIFICATIONS \n6) LOG OUT ");

							int ch1;

							try {

								System.out.print("\nPlease Enter Appropriate Option : ");

								ch1 = sc.nextInt();

								sc.nextLine();

							} catch (Exception e) {

								System.out.println("\n\nEnter Digits ONLY!!");

								System.out.print("\nRe-Enter : ");

								sc.nextLine();

								ch1 = sc.nextInt();

								sc.nextLine();

							}

							if (ch1 == 6) {

								break;

							}

							switch (ch1) {

							case 1:

								obj1.update(alumni_id);

								break;

							case 2:

								obj1.view_profile(alumni_id);

								break;

							case 3:

								String ans1 = g.display(alumni_id);

								if (ans1 == "") {

									System.out.println("\n\nYOU HAVE NO CONNECTIONS!!");

									break;

								}

								System.out.println(
										"\n\n\n\n\n\t\t\t\t\t\t\t\t                YOUR CONNECTIONS                        \t\t\t\t");

								System.out.println(
										"\t\t\t\t\t\t\t\t     ___________________________________\t\t\t\t\n\n");

								obj.display_student(ans1);

								break;

							case 4:

								int ch2 = obj1.alumni_messages();

								switch (ch2) {

								case 1:

									ans1 = g.display(alumni_id);

									if (ans1 == "") {

										System.out.println("\n\nYOU HAVE NO CONNECTIONS!!");

										break;

									}

									System.out.println(
											"\n\n\n\n\n\t\t\t\t\t\t\t\t                YOUR CONNECTIONS                        \t\t\t\t");

									System.out.println(
											"\t\t\t\t\t\t\t\t     ___________________________________\t\t\t\t\n\n");

									obj.display_student(ans1);

									int student;

									try {

										System.out.print("\n\nEnter Unique Identity No. of Student to Message : ");

										student = sc.nextInt();

										sc.nextLine();

									}

									catch (Exception e) {

										System.out.println("Enter Digits Only");

										System.out.println("Re-Enter");

										sc.nextLine();

										student = sc.nextInt();

										sc.nextLine();

									}

									System.out.println("\n\n");

									int c = obj.check(student, ans1);

									if (c == 0) {

										break;

									}

									String std_name = obj.student_name(student);

									String s = std_name.toUpperCase();

									String alumni_name = obj1.alumni_name(alumni_id);

									System.out.println("\n\n\n\n\n\t\t\t\t\t\t\t\t " + s + " \t\t\t\t");
									System.out.println("\t\t\t\t\t\t\t\t _____________________________\t\t\t\t\n\n");

									mess.send_alumni(std_name, alumni_name);

									break;

								case 2:

									break;

								}

								break;

							case 5:

								String ids;

								ids = g.show_notification(alumni_id);

								if (ids.equals("")) {
									System.out.println("\n\nNO NOTIFICATIONS!!");
									break;
								}

								obj.student_name(ids);

								int choice;

								do {
									System.out.println(
											"\nDo you want to....\n\n1. ACCEPT REQUEST\n2. REJECT REQUEST\n3. EXIT");
									System.out.print("\nEnter your choice : ");
									choice = sc.nextInt();
									switch (choice) {
									case 1:
										String connect_ids = "";

										System.out.print("\nEnter Unique IDs you want to connect with:");

										sc.nextLine();

										connect_ids = sc.nextLine().trim(); // Trim the input string

										String notification_id = g.show_notification(alumni_id);

										String[] accepted_ids_array = connect_ids.split(",");

										boolean allacceptedIDsFound = true;

										for (String rejected_id : accepted_ids_array) {

											if (!notification_id.contains(rejected_id)) {

												allacceptedIDsFound = false;
												break;
											}
										}

										if (allacceptedIDsFound) {
											g.accept_request(connect_ids, alumni_id);
										}

										else {
											System.out.print("\nReEnter Unique IDs you want to Reject :");
											connect_ids = sc.nextLine().trim();

											g.accept_request(connect_ids, alumni_id);

										}

										break;

									case 2:

										String reject_ids = "";

										System.out.print("\nEnter Unique IDs you want to Reject :");
										sc.nextLine();
										reject_ids = sc.nextLine().trim();

										String notification_ids = g.show_notification(alumni_id);

										String[] rejected_ids_array = reject_ids.split(",");

										boolean allRejectedIDsFound = true;

										for (String rejected_id : rejected_ids_array) {

											if (!notification_ids.contains(rejected_id)) {

												allRejectedIDsFound = false;
												break;
											}
										}

										if (allRejectedIDsFound) {

											g.reject_request(reject_ids, alumni_id);
										}

										else {

											System.out.print("\nReEnter Unique IDs you want to Reject :");

											reject_ids = sc.nextLine().trim();

											g.reject_request(reject_ids, alumni_id);

										}

										break;

									default:

										System.out.println("\n\nEnter a Valid Option!!");

									}

								} while (choice != 3);

							}

						}

					}
				}

				else {

					System.out.println("\n\nEnter a Valid Option!!");

				}

				break;

			case 2:

				System.out.println("\n\nWho Are You?\n\n1) STUDENT\n2) ALUMNI");

				int num1;

				try {

					System.out.print("\nPlease Enter Appropriate Option : ");

					num1 = sc.nextInt();

					sc.nextLine();

				} catch (Exception e) {

					System.out.println("\n\nEnter Digits ONLY!!");

					System.out.println("\nRe-Enter : ");

					sc.nextLine();

					num1 = sc.nextInt();

					sc.nextLine();

				}

				if (num1 == 1) {

					obj.sign_up();

				}

				else if (num1 == 2) {

					obj1.signup();

				}

				else {

					System.out.println("\nEnter a Valid Option!");

				}

				break;

			case 3:

				System.out.println(

						"\n\n\n\n\n\n\n\n\t\t\t\t\t\t ***************************** THANK YOU *****************************\n\n");

				System.out.println("\t\t\t\t\t\t\t We Hope We've Connected You To Your Desired Mentors");

				System.out.println("\t\t\t\t\t\t And Being An Alumni It's Always A Bliss Reconnecting To Your Roots.");

				return;

			default:

				System.out.println("\n\nEnter a Valid Option!!");

			}

		}

	}

}
