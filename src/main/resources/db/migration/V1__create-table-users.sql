CREATE TABLE IF NOT EXISTS users (
	id serial not null primary key,
	firstname varchar(150) not null,
	lastname varchar(150) not null,
	document varchar(11) not null unique,
	email varchar(150) not null unique,
	password varchar(150) not null,
	balance decimal,
	user_type varchar(100) not null
);

CREATE TABLE IF NOT EXISTS transactions (
	id serial not null primary key,
	amount decimal not null,
	receiver_id int not null,
	sent_id int not null,
	timestamp date not null,
	constraint FK_ReceiverId foreign key (receiver_id) references users(id),
    constraint FK_SentId foreign key (sent_id) references users(id)
);