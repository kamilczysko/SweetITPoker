package com.walczak.itpoker.domain.participant;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.dto.PlayerDTO;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void updateAllParticipants(List<Player> players) {
        playerRepository.saveAll(players);
    }
    public void updateParticipant(PlayerDTO playerDTO) {
        Player player = Mapper.mapToParticipant(playerDTO);
        playerRepository.save(player);
    }

    @Deprecated
    public Iterable<Player> getAll() {
        return playerRepository.findAll();
    }

    public void removeParticipant(String id) {
        playerRepository.findById(id).map(p -> Player.builder(p).obsoleted(true).build())
                        .ifPresent(playerRepository::save);
    }
}
