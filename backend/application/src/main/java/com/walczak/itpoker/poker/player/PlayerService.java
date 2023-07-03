package com.walczak.itpoker.poker.player;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(String playerId) {
        playerRepository.deleteById(UUID.fromString(playerId));
    }

    public Player getPlayerById(String id) {
        return playerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalStateException("Player not found: " + id));
    }

    public void resetAllPlayers(List<String> players) {
        List<UUID> allPlayers = players.stream()
                .map(UUID::fromString).toList();
        List<Player> playersToReset = StreamSupport.stream(playerRepository.findAllById(allPlayers).spliterator(), false)
                .map(Player::cleanVote)
                .collect(Collectors.toList());
        playerRepository.saveAll(playersToReset);
    }

    public void updatePlayer(Player player) {
        playerRepository.findById(player.getId())
                .ifPresent(playerRepository::save);
    }

}
