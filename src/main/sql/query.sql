CREATE TABLE Chat (
                      chat_id bigint PRIMARY KEY ,
                      is_started bool DEFAULT FALSE,
                      explaining_person bigint UNIQUE,
                      word varchar(30)
)
CREATE TABLE Word (
                      id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                      word varchar(30)
)
CREATE TABLE Player (
                        id bigint PRIMARY KEY,
                        points int DEFAULT 0,
                        chat_id bigint REFERENCES chat(chat_id) ON DELETE CASCADE
)