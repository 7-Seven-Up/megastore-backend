INSERT INTO users (user_id, username, password, email, full_name, activated, deleted, phone_number)
VALUES ('1f8b460d-3575-4c60-961b-fb15116454d1','user test', 'password', 'email@gmail.com', 'Client Name', false, false, 12345),
       ('06ca2421-8be3-4cb8-92b6-32a2fe6266e9','user test 2', 'password 2', 'email2@gmail.com', 'Client Name 2', true, false, 12345);

INSERT INTO tokens (token_id, token_expiration_date, user_user_id)
VALUES ('834fe4c1-d77d-4bf0-8e29-3cddbc245416', DATEADD('HOUR', 1, CURRENT_TIMESTAMP), '1f8b460d-3575-4c60-961b-fb15116454d1'),
       ('7927fb16-d23b-43f5-95c8-6c82bb333331', DATEADD('HOUR', 1, CURRENT_TIMESTAMP), '06ca2421-8be3-4cb8-92b6-32a2fe6266e9');