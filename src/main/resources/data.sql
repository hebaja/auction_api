INSERT INTO auctioneer(name, email) VALUES('auctioneer', 'hebajabackup@gmail.com');
INSERT INTO auctioneer(name, email) VALUES('Feanor', 'henrique.jarbas@cna.com.br');

INSERT INTO auctioneer_roles(auctioneer_id, role) values(1, 'ROLE_AUCTIONEER');
INSERT INTO auctioneer_roles(auctioneer_id, role) values(2, 'ROLE_AUCTIONEER');

INSERT INTO auction(title, auctioneer_id, finished, favorite) values ('First Auction', 1, false, false);
INSERT INTO auction(title, auctioneer_id, finished, favorite) values ('Second Auction', 1, false, false);
INSERT INTO auction(title, auctioneer_id, finished, favorite) values ('Third Auction', 1, false, false);

INSERT INTO lot(auction_id, active, title, description, correct) values (1, false, 'First Lot', 'First lot description', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (1, false, 'Second Lot', 'Second lot description', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (1, false, 'Third Lot', 'Third lot description', false);

INSERT INTO lot(auction_id, active, title, description, correct) values (2, false, 'Nintendo Switch', 'Hybrid video game console', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (2, false, 'Playstation 5', 'Last release', false);

INSERT INTO lot(auction_id, active, title, description, correct) values (3, false, 'Stella 2', '3d printer', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (3, false, 'Ender 3', '3d printer', false);

INSERT INTO auction_lots(auction_id, lots_id) values(1, 1);
INSERT INTO auction_lots(auction_id, lots_id) values(1, 2);
INSERT INTO auction_lots(auction_id, lots_id) values(1, 3);

INSERT INTO auction_lots(auction_id, lots_id) values(2, 4);
INSERT INTO auction_lots(auction_id, lots_id) values(2, 5);

INSERT INTO auction_lots(auction_id, lots_id) values(3, 6);
INSERT INTO auction_lots(auction_id, lots_id) values(3, 7);

INSERT INTO group_player(name, auctioneer_id, active) values('Test Group', 1, false);
INSERT INTO group_player(name, auctioneer_id, active) values('New Group', 1, false);

INSERT INTO wallet(money) values('600.0');
INSERT INTO wallet(money) values('600.0');

INSERT INTO wallet(money) values('600.0');
INSERT INTO wallet(money) values('600.0');

INSERT INTO player(name, wallet_id, group_player_id) values('First player', 1, 1);
INSERT INTO player(name, wallet_id, group_player_id) values('Second player', 2, 1);

INSERT INTO player(name, wallet_id, group_player_id) values('Terry Bogard', 3, 2);
INSERT INTO player(name, wallet_id, group_player_id) values('Andy Bogard', 4, 2);

INSERT INTO group_player_players(group_player_id, players_id) values(1, 1);
INSERT INTO group_player_players(group_player_id, players_id) values(1, 2);

INSERT INTO group_player_players(group_player_id, players_id) values(2, 3);
INSERT INTO group_player_players(group_player_id, players_id) values(2, 4);



INSERT INTO auction(title, auctioneer_id, finished, favorite) values ('Capcom', 2, false, false);
INSERT INTO auction(title, auctioneer_id, finished, favorite) values ('SNK', 2, false, false);

INSERT INTO lot(auction_id, active, title, description, correct) values (4, false, 'SF', 'Street Fighter', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (4, false, 'DS', 'DarkStalkers', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (4, false, 'JJ', 'Jojo Bizarre adventure', false);

INSERT INTO lot(auction_id, active, title, description, correct) values (5, false, 'FF', 'Fatal Fury', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (5, false, 'SS', 'Samurai Shodown', false);

INSERT INTO auction_lots(auction_id, lots_id) values(4, 8);
INSERT INTO auction_lots(auction_id, lots_id) values(4, 9);
INSERT INTO auction_lots(auction_id, lots_id) values(4, 10);

INSERT INTO auction_lots(auction_id, lots_id) values(5, 11);
INSERT INTO auction_lots(auction_id, lots_id) values(5, 12);

INSERT INTO group_player(name, auctioneer_id, active) values('Capcom fighters', 2, false);
INSERT INTO group_player(name, auctioneer_id, active) values('SNL fighters', 2, false);

INSERT INTO wallet(money) values('300.0');
INSERT INTO wallet(money) values('300.0');

INSERT INTO wallet(money) values('300.0');
INSERT INTO wallet(money) values('300.0');

INSERT INTO player(name, wallet_id, group_player_id) values('Ryu', 5, 3);
INSERT INTO player(name, wallet_id, group_player_id) values('Ken', 6, 3);

INSERT INTO player(name, wallet_id, group_player_id) values('Haohmaru', 7, 4);
INSERT INTO player(name, wallet_id, group_player_id) values('Kaede', 8, 4);

INSERT INTO group_player_players(group_player_id, players_id) values(3, 5);
INSERT INTO group_player_players(group_player_id, players_id) values(3, 6);

INSERT INTO group_player_players(group_player_id, players_id) values(4, 7);
INSERT INTO group_player_players(group_player_id, players_id) values(4, 8);


INSERT INTO auction(title, auctioneer_id, finished, favorite, favorited_auctioneer_id, favorited_auction_id) values ('SNK', 1, false, true, 2, 6);

INSERT INTO lot(auction_id, active, title, description, correct) values (6, false, 'FF', 'Fatal Fury', true);
INSERT INTO lot(auction_id, active, title, description, correct) values (6, false, 'SS', 'Samurai Shodown', false);

INSERT INTO auction_lots(auction_id, lots_id) values(6, 13);
INSERT INTO auction_lots(auction_id, lots_id) values(6, 14);


