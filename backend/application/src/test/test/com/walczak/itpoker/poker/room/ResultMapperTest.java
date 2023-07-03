package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.poker.player.Player;
import com.walczak.itpoker.poker.player.PlayerRole;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ResultMapperTest {

    @Test
    public void testForEmptyList() {
        double avg = ResultMapper.getAvg(List.of());

        assertEquals(0, avg, 0.0);
    }

    @Test
    public void testForPlayersOfDifferentUnits() {
        Player player1 = Player.builder()
                .role(PlayerRole.DEVELOPER)
                .selectedUnit("d")
                .selectedCard("1")
                .build();
        Player player2 = Player.builder()
                .role(PlayerRole.DEVELOPER)
                .selectedUnit("h")
                .selectedCard("3")
                .build();

        double avg = ResultMapper.getAvg(List.of(player1, player2));

        assertEquals(5.5, avg, 0.0);
    }
}