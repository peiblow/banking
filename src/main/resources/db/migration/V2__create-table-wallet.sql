CREATE TABLE IF NOT EXISTS wallets (
	id serial not null primary key,
	walletOwner int not null,
	brl decimal not null,
	usd decimal not null,
	btc decimal not null,
	createdAt date not null,
	updatedAt date not null,
	constraint FK_WalletOwner foreign key (walletOwner) references users(id)
);
