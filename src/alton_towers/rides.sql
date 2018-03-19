-- src/alton_towrs/rides.sql
-- Alton Towers ride wait times

-- :name create-ride-table :!
-- :doc Create the ride table
create table ride (
  id         int not null,
  `name`     varchar(50) not null
)

-- :name create-times-table :!
-- :doc Create the times table
create table times (
  id         int not null primary key auto_increment,
  `rideId`   int not null,
  `date`     datetime not null,
  `time`     int,
  status     varchar(50),
  `group`    varchar(50)
);

-- :name add-ride :!
-- :doc Add a new ride
insert ignore
into ride (id, `name`)
values (:ride-id, :ride)

-- :name add-time :!
-- :doc Add a new time for a ride
insert
into times (`rideId`, `date`, `time`, status, `group`)
values (:ride-id, :date, :time, :status, :group)

-- :name get-times :? :*
-- :doc Get all times
select r.`name`, t.`time`, t.`date`, t.`status`, t.`group`
from times as t
inner join ride as r on r.id = t.`rideId`
