

-- Create tables
use `fortress`;

    create table Ranks (
        rankNo          integer         not null    unique  auto_increment,
        name            varchar(128)     not null    unique,
        privilegeLevel  integer         not null,
        
        primary key (rankNo)
    );
    
    
    
    create table Personnel (
         personNo       integer         not null    unique  auto_increment,
         name           varchar(128)    not null,
         age            integer,
         sex            char   (  1),
         rankNo         integer,
         salary         decimal(9,2),
         loginName      varchar(128)    not null    unique,
         loginHash      char   ( 64)    not null,
         loginSalt      char   ( 16)    not null,
         
         primary key (personNo),
         foreign key (rankNo) references Ranks(rankNo)
    );
    
    
    
    create table Messages (
        messageNo       integer         not null    unique  auto_increment,
        sentDate        timestamp       not null,
        subject         varchar(128),
        body            text,
        sentBy          integer         not null,          
        sentTo          integer         not null,
        
        primary key (messageNo),
        foreign key (sentBy) references Personnel(personNo),
        foreign key (sentTo) references Personnel(personNo)
    );
    
    
    
    create table Orders (
        orderNo         integer         not null    unique  auto_increment,
        messageNo       integer         not null    unique,
        isRead          boolean         not null,
        isDone          boolean         not null,
        
        primary key (orderNo),
        foreign key (messageNo) references Messages(messageNo)
    );
    
    
    
    create table Reports (
        reportNo        integer         not null    unique  auto_increment,
        messageNo       integer         not null    unique,
        isRead          boolean         not null,
        
        primary key (reportNo),
        foreign key (messageNo) references Messages(messageNo)
    );
    
    
    
    create table Supplies (
        supplyNo        integer         not null    unique  auto_increment,
        name            varchar(128)    not null,
        count           integer         not null,
        
        primary key (supplyNo)
    );
    
    
    
    create table Squads (
        squadNo         integer         not null    unique  auto_increment,
        name            varchar(128)    not null    unique,
        commander       integer                        unique,
        
        primary key (squadNo)
    );
    
    
    
    create table Vehicles (
        vehicleNo       integer         not null    unique  auto_increment,
        type            varchar(128)    not null,
        personNo        integer,
        
        primary key (vehicleNo),
        foreign key (personNo) references Personnel(personNo)
    );
    
    
    
    create table SquadTroops (
        squadNo         integer        not null,
        personNo        integer        not null,
        
        foreign key (squadNo)  references Squads   (squadNo),
        foreign key (personNo) references Personnel(personNo)
    );
    
    
    
    create table SquadVehicles (
        squadNo         integer        not null,
        vehicleNo       integer        not null,
        
        foreign key (squadNo)   references Squads  (squadNo),
        foreign key (vehicleNo) references Vehicles(vehicleNo)
    );
    
    
    
    create table SquadSupplies (
        squadNo         integer        not null,
        supplyNo        integer        not null,
        count           integer        not null,
        
        foreign key (squadNo)  references Squads  (squadNo),
        foreign key (supplyNo) references Supplies(supplyNo)
    );
    
    
    
    create table Battles (
        battleNo        integer         not null    unique  auto_increment,
        datetime        timestamp       not null,
        reportNo        integer,
        
        primary key (battleNo),
        foreign key (reportNo) references Reports(reportNo)
    );
    
    
    
    create table BattleSquads (
        battleNo        integer         not null,
        squadNo         integer         not null,
        
        foreign key (battleNo) references Battles(battleNo),
        foreign key (squadNo)  references Squads (squadNo)
    );
    