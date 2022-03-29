insert into persons(person_name)
values ('Alfred'),
       ('Birgit'),
       ('Cäsar');

insert into events(event_name, begin_time, end_time)
values ('Brunch', '2022-01-01 08:00:00', '2022-01-01 11:00:00'),
       ('Presentation', '2022-01-03 13:00:00', '2022-01-03 14:30:00'),
       ('Opera', '2022-01-02 19:00:00', '2022-01-02 22:20:00'),
       ('Theater', '2022-01-02 17:00:00', '2022-01-02 19:30:00');

insert into persons_events(person_id, event_id)
values (1, 1),
       (2, 1),
       (1, 2),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3);