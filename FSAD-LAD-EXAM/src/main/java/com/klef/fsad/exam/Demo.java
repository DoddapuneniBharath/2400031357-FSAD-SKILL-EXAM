package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.Scanner;

public class Demo {

    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure().buildSessionFactory();
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Insert Department");
            System.out.println("2. Delete Department");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(choice) {
                case 1:
                    insertDepartment(sc);
                    break;
                case 2:
                    deleteDepartment(sc);
                    break;
                case 3:
                    factory.close();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void insertDepartment(Scanner sc) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Departement dept = new Departement();

            System.out.print("Enter Department Name: ");
            dept.setName(sc.nextLine());

            System.out.print("Enter Description: ");
            dept.setDescription(sc.nextLine());

            dept.setDate(new Date());  // current date

            System.out.print("Enter Status: ");
            dept.setStatus(sc.nextLine());

            session.save(dept);
            tx.commit();
            System.out.println("Department inserted with ID: " + dept.getId());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void deleteDepartment(Scanner sc) {
        System.out.print("Enter Department ID to delete: ");
        int id = sc.nextInt();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Departement dept = session.get(Departement.class, id);
            if (dept != null) {
                session.delete(dept);
                tx.commit();
                System.out.println("Department deleted.");
            } else {
                System.out.println("Department not found.");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}