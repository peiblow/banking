CREATE TABLE IF NOT EXISTS wallets (
	id serial not null primary key,
	wallet_owner int not null,
	brl decimal not null,
	usd decimal not null,
	btc decimal not null,
	created_at date not null,
	updated_at date not null,
	constraint FK_WalletOwner foreign key (wallet_owner) references users(id)
);
