package code;

import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */
	private BinarySearchTree<String, ContactInfo> names;
	private BinarySearchTree<String, ContactInfo> numbers;

	public PhoneBook() {
		names = new BinarySearchTree<String, ContactInfo>();
		numbers = new BinarySearchTree<String, ContactInfo>();
		names.setComparator(new DefaultComparator<String>());
		numbers.setComparator(new DefaultComparator<String>());
	}

	// Returns the number of contacts in the phone book
	public int size() {
		return names.size();
	}

	// Returns true if the phone book is empty, false otherwise
	public boolean isEmpty() {
		return names.isEmpty();
	}

	//Adds a new contact or overwrites an existing contact with the given info
	// Args should be given in the order of e-mail and address which is handled for
	// you
	// Note that it can also be empty. If you do not want to update a field pass
	// null
	public void addContact(String name, String number, String... args) {
		int numArgs = args.length;
		String email = null;
		String address = null;

		/*
		 * Add stuff here if needed
		 */

		if (numArgs > 0)
			if (args[0] != null)
				email = args[0];
		if (numArgs > 1)
			if (args[1] != null)
				address = args[1];
		if (numArgs > 2)
			System.out.println("Ignoring extra arguments");

		/*
		 * TO BE IMPLEMENTED
		 * 
		 */
		ContactInfo contact = new ContactInfo(name, number);
		contact.setAddress(address);
		contact.setEmail(email);
		names.put(name, contact);
		numbers.put(number, contact);
	}

	// Return the contact info with the given name
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByName(String name) {
		ContactInfo a = names.get(name);
		if(a != null) {
			return a;
		}
		return null;
	}

	// Return the contact info with the given phone number
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByPhone(String phoneNumber) {
		ContactInfo a = numbers.get(phoneNumber);
		if(a != null) {
			return a;
		}
		return null;
	}

	// Return the contact info with the given e-mail
	// Return null if it does not exists
	// Can be O(n)
	public ContactInfo searchByEmail(String email) {
		ContactInfo contact = null;
		for (String k : names.keySet()) {
			BinaryTreeNode node = names.findNode(names.getRoot(), k);
			String em = ((ContactInfo) node.getValue()).getEmail();
			if (em != null && em.equals(email)) {
				contact = (ContactInfo) node.getValue();
				break;
			}
		}
		return contact;
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given name to delete, false otherwise
	public boolean removeByName(String name) {
		BinaryTreeNode<String, ContactInfo> contact = names.getNode(name);
		if(contact != null) {
			String phoneNo = ((ContactInfo) contact.getValue()).getNumber();
			names.remove(name);
			numbers.remove(phoneNo);
			return true;
		}
		return false;
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given number to delete, false otherwise
	public boolean removeByNumber(String phoneNumber) {
		BinaryTreeNode<String, ContactInfo> contact = numbers.getNode(phoneNumber);
		if(contact != null) {
			String name = ((ContactInfo) contact.getValue()).getName();
			names.remove(name);
			numbers.remove(phoneNumber);
			return true;
		}
		return false;
	}

	// Returns the number associated with the name
	public String getNumber(String name) {
		BinaryTreeNode<String, ContactInfo> c = names.getNode(name);
		if(c != null) {
			return ((ContactInfo) c.getValue()).getNumber();
		}
		return null;
	}

	// Returns the name associated with the number
	public String getName(String number) {
		BinaryTreeNode<String, ContactInfo> c = numbers.getNode(number);
		if(c != null) {
			return ((ContactInfo) c.getValue()).getName();
		}
		return null;
	}

	// Update the email of the contact with the given name
	// Returns true if there is a contact with the given name to update, false otherwise
	public boolean updateEmail(String name, String email) {
		BinaryTreeNode<String, ContactInfo> c = names.getNode(name);
		if(c != null) {
			((ContactInfo) c.getValue()).setEmail(email);
			return true;
		}
		return false;
	}

	// Update the address of the contact with the given name
	// Returns true if there is a contact with the given name to update, false otherwise
	public boolean updateAddress(String name, String address) {
		BinaryTreeNode<String, ContactInfo> c = names.getNode(name);
		if(c != null) {
			((ContactInfo) c.getValue()).setAddress(address);
			return true;
		}
		return false;
	}

	// Returns a list containing the contacts in sorted order by name
	public List<ContactInfo> getContacts() {
		List<ContactInfo> contacts = null;
		for(BinaryTreeNode<String, ContactInfo> node : names.getNodesInOrder()) {
			contacts.add((ContactInfo) node.getValue());
		}
		return contacts;
	}

	// Prints the contacts in sorted order by name
	public void printContacts() {
		
		for (BinaryTreeNode<String, ContactInfo> node : names.getNodesInOrder()) {
			System.out.println(names.get((String) node.getKey()));		
		}
	}

}
