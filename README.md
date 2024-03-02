Project Goal

The organization requires an E-Voting System to conduct online elections. The system should allow users to create elections, cast votes, and monitor election results. The application will be built using Spring Boot, Hibernate, JPA, Spring Security and MySQL for the database. It should include CRUD operations to manage elections, election candidates, and votes. It should also include Authentication and Authorisation for users. 

Features of the Application:

    • User registration and login functionality with password hashing for enhanced security.
    • JWT token generation upon successful authentication, allowing users to access protected 
      resources without the need for session cookies
    • Implementation of role-based access control (RBAC) to enforce authorization policies based 
      on user roles and permissions.
    • Administrators can create new elections, specify the list of candidates for each election, set 
      voting periods, and monitor voting progress in real-time.
    • Voters can cast votes for a specific election and candidate within the start  
      and end date of election
    • Results generation with count of votes for each candidate participating in the election.
    • Comprehensive error handling and logging mechanisms to ensure system reliability and 
      traceability.

Flowchart:

![image](https://github.com/Vishesh-Jindal/EVotingSytem/assets/84375861/e9e44bb8-ce20-4af9-ba83-7cb21d3a4d9e)
