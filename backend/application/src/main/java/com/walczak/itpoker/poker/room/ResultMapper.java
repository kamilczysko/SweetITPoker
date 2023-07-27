package com.walczak.itpoker.poker.room;

import com.walczak.itpoker.poker.player.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultMapper {
    private final static Map<String, Integer> UNITS = Map.of("d", 8, "sp", 1, "h", 1);

    public static double getAvg(List<Player> players) {
        List<Player> filteredPlayers = players.stream()
                .filter(player -> !player.hasSelectedNoValueCard())
                .toList();
        int numberOfVotes = filteredPlayers.size();
        if (numberOfVotes == 0) {
            return 0.0;
        }
        Map<String, List<String>> unitToValues = filteredPlayers.stream()
                .collect(Collectors.groupingBy(Player::getSelectedUnit,
                        Collectors.mapping(Player::getSelectedCard, Collectors.toList())
                ));

        int totalSum = unitToValues.entrySet().stream()
                .mapToInt(entry -> sum(entry.getKey(), entry.getValue()))
                .sum();
        return (totalSum * 1.0d) / numberOfVotes;
    }

    private static int sum(String unit, List<String> values) {
        return values.stream()
                .map(ResultMapper::convertValue)
                .mapToInt(value -> value * UNITS.get(unit))
                .sum();
    }

    private static int convertValue(String value) {
        if (value.equals("q") || value.equals("c")) {
            return 0;
        }
        return Integer.parseInt(value);
    }

}
