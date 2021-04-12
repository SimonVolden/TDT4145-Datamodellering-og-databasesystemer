-- Creating the database:

create table User (
UserID int not null,
Username varchar(30),
Email varchar(50),
Password varchar(50),
constraint User_PK primary key (UserID));

create table Instructor (
UserID int not null,
InstructorID int not null,
constraint InstructorID_PK primary key (InstructorID),
constraint User_FK foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);

create table Student (
UserID int not null,
StudentID int not null,
constraint StudentID_PK primary key (StudentID),
constraint User_FK2 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);
    
create table Course (
CourseNumber int not null,
CourseID varchar(20) not null,
CourseName varchar(50) not null,
Term varchar(20) not null,
Year int not null,
constraint Course_PK primary key (CourseNumber));


create table Invite (
InstructorID int not null,
UserID int not null,
CourseNumber int not null,
constraint Invite_PK primary key (InstructorID, UserID, CourseNumber),
constraint InstructorID_FK foreign key (InstructorID) references Instructor(InstructorID)
	on update cascade
    on delete cascade,
constraint User_FK3 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade,
constraint Course_FK foreign key (CourseNumber) references Course(CourseNumber)
	on update cascade
    on delete cascade);

    
create table InstructorInCourse (
InstructorID int not null,
CourseNumber int not null,
AllowAnonymousPosts boolean not null,
constraint InstructorInCourse_PK primary key (InstructorID, CourseNumber),
constraint InstructorID_FK2 foreign key (InstructorID) references Instructor(InstructorID)
	on update cascade
    on delete cascade,
constraint Course_FK2 foreign key (CourseNumber) references Course(CourseNumber)
	on update cascade
    on delete cascade);
    

create table StudentInCourse (
StudentID int not null,
CourseNumber int not null,
constraint StudentInCourse_PK primary key (StudentID, CourseNumber),
constraint StudentID_FK foreign key (StudentID) references Student(StudentID)
	on update cascade
    on delete cascade,
constraint Course_FK3 foreign key (CourseNumber) references Course(CourseNumber)
	on update cascade
    on delete cascade);
    
create table Folder (
FolderID int not null,
Title varchar(50) not null,
CourseNumber int not null,
ParentFolderID int,
constraint FolderID_PK primary key (FolderID),
constraint Course_FK4 foreign key (CourseNumber) references Course(CourseNumber)
	on update cascade
    on delete cascade,
constraint ParentFolderID_FK foreign key (ParentFolderID) references Folder(FolderID)
	on update cascade
    on delete cascade);

create table Post (
PostID int not null,
UserID int not null,
Title varchar(100),
Content varchar(500),
Anonymous boolean not null,
Likes int,
constraint PostID_PK primary key (PostID),
constraint UserID_FK foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);
    
create table InstructorAnswer (
PostID int not null,
UserID int not null,
Content varchar(500),
Likes int,
constraint PostID_PK1 primary key (PostID),
constraint PostID_FK1 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint UserID_FK1 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);
    
create table StudentAnswer (
PostID int not null,
UserID int not null,
Content varchar(500),
Anonymous boolean not null,
Likes int,
constraint PostID_PK2 primary key (PostID),
constraint PostID_FK2 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint UserID_FK2 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);   
    
create table FollowupDiscussion (
PostID int not null,
SequenceNumber int not null,
UserID int not null,
Content varchar(500),
Anonymous boolean not null,
Likes int,
constraint FollowupDiscussion_PK primary key (PostID, SequenceNumber),
constraint PostID_FK3 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint UserID_FK3 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);   
    
create table Reply (
ReplyID int not null,
PostID int not null,
UserID int not null,
Content varchar(500),
Anonymous boolean not null,
Likes int,
constraint ReplyID primary key (ReplyID),
constraint PostID_FK4 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint UserID_FK4 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade);   
    
create table Colours (
ColourID int not null,
Colour varchar(15),
constraint ColourID_PK primary key (ColourID));

create table PostColour (
ColourID int not null,
PostID int not null,
constraint PostColour_PK primary key (ColourID, PostID),
constraint ColourID_FK foreign key (ColourID) references Colours(ColourID)
	on update cascade
    on delete cascade,
constraint PostID_FK5 foreign key (PostID) references Post(PostID)
on update cascade
on delete cascade);

create table Likes (
UserID int not null,
PostID int not null,
constraint Likes_PK primary key (UserID, PostID),
constraint PostID_FK6 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint UserID_FK6 foreign key (UserID) references User(UserID)
	on update cascade
    on delete cascade); 
    
create table Link (
LinkID int not null,
PostID int not null,
constraint Link_PK primary key (LinkID, PostID),
constraint PostID_FK7 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade);
    
create table LinkInReply (
LinkID int not null,
PostID int not null,
InstructorID int,
StudentID int,
SequenceNumber int,
ReplyID int,
constraint LinkInReply_PK primary key (LinkID, PostID, InstructorID, StudentID, SequenceNumber, ReplyID),
constraint LinkID_FK foreign key (LinkID) references Link(LinkID)
	on update cascade
    on delete cascade,
constraint PostID_FK8 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade,
constraint InstructorID_FK1 foreign key (InstructorID) references Instructor(InstructorID)
	on update cascade
    on delete cascade,
constraint StudentID_FK1 foreign key (StudentID) references Student(StudentID)
	on update cascade
    on delete cascade,
constraint SequenceNumber_FK1 foreign key (PostID, SequenceNumber) references FollowupDiscussion(PostID, SequenceNumber)
	on update cascade
    on delete cascade,
constraint ReplyID_FK1 foreign key (ReplyID) references Reply(ReplyID)
	on update cascade
    on delete cascade);

create table Tags (
TagID int not null,
Tag varchar(50) not null,
constraint TagID_PK primary key (TagID));

create table TagInPost (
TagID int not null,
PostID int not null,
constraint TagInPost_PK primary key (TagID, PostID),
constraint TagID_FK foreign key (TagID) references Tags(TagID)
	on update cascade
    on delete cascade,
constraint PostID_FK9 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade);
    
create table PostInFolder (
FolderID int not null,
PostID int not null,
constraint PostInFolder_PK primary key (FolderID, PostID),
constraint FolderID_FK foreign key (FolderID) references Folder(FolderID)
	on update cascade
    on delete cascade,
constraint PostID_FK10 foreign key (PostID) references Post(PostID)
	on update cascade
    on delete cascade);

create table PostRead (
UserID int not null,
PostID int not null,
constraint PostRead_PK primary key (UserID, PostID));



-- Delete from tables
delete from course;
delete from User;
delete from post;
select * from post;
delete from postRead;
delete from postinfolder;
delete from instructor;
delete from student;
delete from folder;
delete from tags;
delete from taginpost;
delete from followupdiscussion;

-- Setup:
-- Course
insert into course values (1, "TDT4100", "Objektorientert programmering", "Spring", 2021);

-- Folders
insert into folder values (0, "PARENTFOLDER", 1, null);
insert into folder values (1, "Øving 1", 1, 0);
insert into folder values (2, "Øving 2", 1, 0);
insert into folder values (3, "Øving 3", 1, 0);
insert into folder values (4, "Øving 4", 1, 0);
insert into folder values (5, "Øving 5", 1, 0);
insert into folder values (6, "Prosjekt", 1, 0);
insert into folder values (7, "Eksamen", 1, 0);
insert into folder values (8, "Other", 1, 0);

-- Tags
insert into tags values (1, "Logistikk");
insert into tags values (2, "Midterm");
insert into tags values (3, "Forelesninger");
insert into tags values (4, "Treningsoppgaver");
insert into tags values (5, "Spørsmål");

-- Users
select * from user;
insert into user values (1, "olanordmann", "ola@nordmann.no", "test");
insert into user values (2, "karinordmann", "kari@nordmann.no", "test");
insert into user values (3, "testinstructor", "testinstructor", "test");
insert into user values (4, "teststudent", "teststudent", "test");

-- Instructors
insert into instructor values (3, 1);

-- Students
insert into student values (4, 1);

-- Posts
insert into post values (1, 1, "TestTitle", "This post is in øving 3 with tag Midterm", 0, 0);
insert into post values (2, 2, "TestTitle", "This post is in øving 4 with tag Forelesninger", 0, 0);

-- Add post to folder
insert into postinfolder values (3, 1);
insert into postinfolder values (4, 2);

-- Add tag to post
insert into taginpost values (3, 1);
insert into taginpost values (4, 2);

-- Followupdiscussion
insert into followupdiscussion values (1, 1, 1, "Test followup", 0, 0);
insert into followupdiscussion values (1, 2, 1, "Test followup", 0, 0);
