package com.task3.contactManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ContactManager {
	private static final Map<String, Contact> contactList = new HashMap<>();
	private static final Scanner sc = new Scanner(System.in);
	private static final String FILE_NAME = "src/com/task3/contactmanager/contacts.txt";

	public static void main(String[] args) {
		loadContactsFromFile();

		int choice;

		do {
			System.out.println("Contact Manager Menu:");
			System.out.println("1. Add a new contact");
			System.out.println("2. View contact list");
			System.out.println("3. Edit a contact");
			System.out.println("4. Delete a contact");
			System.out.println("5. Save and exit");
			System.out.print("Enter your choice: ");

			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				addContact();
				break;
			case 2:
				viewContactList();
				break;
			case 3:
				editContact();
				break;
			case 4:
				deleteContact();
				break;
			case 5:
				saveContactsToFile();
				System.out.println("Contacts saved. Exiting the Contact Manager. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please enter a number between 1 and 5.");
			}

		} while (choice != 5);

		sc.close();
	}

	private static void addContact() {
		System.out.print("Enter name: ");
		String name = sc.nextLine();
		System.out.print("Enter phone number: ");
		String phoneNumber = sc.nextLine();
		System.out.print("Enter email address: ");
		String emailAddress = sc.nextLine();

		Contact contact = new Contact(name, phoneNumber, emailAddress);
		contactList.put(name, contact);
		System.out.println("Contact added successfully!");
	}

	private static void viewContactList() {
		if (contactList.isEmpty()) {
			System.out.println("Contact list is empty.");
		} else {
			System.out.println("Contact List:");
			for (Map.Entry<String, Contact> entry : contactList.entrySet()) {
				System.out.println(entry.getValue());
			}
		}
	}

	private static void editContact() {
		System.out.print("Enter the name of the contact to edit: ");
		String name = sc.nextLine();

		if (contactList.containsKey(name)) {
			System.out.print("Enter new phone number: ");
			String newPhoneNumber = sc.nextLine();
			System.out.print("Enter new email address: ");
			String newEmailAddress = sc.nextLine();

			Contact updatedContact = new Contact(name, newPhoneNumber, newEmailAddress);
			contactList.put(name, updatedContact);
			System.out.println("Contact updated successfully!");
		} else {
			System.out.println("Contact not found.");
		}
	}

	private static void deleteContact() {
		System.out.print("Enter the name of the contact to delete: ");
		String name = sc.nextLine();

		if (contactList.containsKey(name)) {
			contactList.remove(name);
			System.out.println("Contact deleted successfully!");
		} else {
			System.out.println("Contact not found.");
		}
	}

	private static void saveContactsToFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(contactList);
		} catch (IOException e) {
			System.out.println("Error saving contacts to file: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private static void loadContactsFromFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			Object obj = ois.readObject();
			if (obj instanceof Map) {
				contactList.putAll((Map<String, Contact>) obj);
			}
		} catch (FileNotFoundException e) {
			System.out.println("No previous contacts found. Creating a new contacts file.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading contacts from file: " + e.getMessage());
		}
	}

	@SuppressWarnings("serial")
	private static class Contact implements Serializable {
		private String name;
		private String phoneNumber;
		private String emailAddress;

		public Contact(String name, String phoneNumber, String emailAddress) {
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.emailAddress = emailAddress;
		}

		@Override
		public String toString() {
			return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
		}
	}
}
