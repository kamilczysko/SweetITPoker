package com.walczak.itpoker.domain.participant;

import com.walczak.itpoker.domain.common.Mapper;
import com.walczak.itpoker.dto.PlayerDTO;
import org.springframework.stereotype.Controller;

import java.util.List;

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
        playerRepository.deleteById(id);
    }
}
