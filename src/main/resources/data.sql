INSERT INTO auctioneer(name, email) VALUES('auctioneer', 'hebajabackup@gmail.com');

INSERT INTO auctioneer_roles(auctioneer_id, role) values(1, 'ROLE_AUCTIONEER');

INSERT INTO auction(title, auctioneer_id, finished) values ('First Auction', 1, false);
INSERT INTO auction(title, auctioneer_id, finished) values ('Second Auction', 1, false);
INSERT INTO auction(title, auctioneer_id, finished) values ('Third Auction', 1, false);

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