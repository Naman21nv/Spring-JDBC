insert into Student (rollno, name, marks) values (4, 'Karna', 85) on conflict (rollno) do nothing;
insert into Student (rollno, name, marks) values (2, 'Arjuna', 98) on conflict (rollno) do nothing;
insert into Student (rollno , name , marks) values (3, 'Yudhishthira', 90) on conflict (rollno) do nothing;
