package com.walczak.itpoker.poker.player;

import com.walczak.itpoker.configuration.PokerLogger;
import com.walczak.itpoker.poker.common.Mapper;
import com.walczak.itpoker.dto.PlayerDTO;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlayerService {
    private final PlayerRepository playerRepository;

    private final PokerLogger logger;

    public PlayerService(PlayerRepository playerRepository, PokerLogger logger) {
        this.playerRepository = playerRepository;
        this.logger = logger;
    }

    public void updateAllPlayers(List<Player> players) {
        List<Player> obsoletedPlayers = players.stream().filter(Player::isObsoleted).collect(Collectors.toList());
        playerRepository.deleteAll(obsoletedPlayers);

        ArrayList<Player> playersToRemove = new ArrayList<>(players);
        playersToRemove.removeAll(obsoletedPlayers);

        playerRepository.saveAll(playersToRemove);
    }
    public void updatePlayer(PlayerDTO playerDTO) {
        Player player = Mapper.mapToPlayer(playerDTO);
        logger.info("Player update: "+player);
        playerRepository.save(player);
    }

    @Deprecated
    public Iterable<Player> getAll() {
        return playerRepository.findAll();
    }

    public void obsoletePlayer(String id) {
        playerRepository.findById(id)
                .map(p -> Player.builder(p).obsoleted(true).build())
                .ifPresent(playerRepository::save);
    }
    public void removePlayer(String id) {
        playerRepository.deleteById(id);
    }
}
