package com.walczak.itpoker.dto;

public record ParticipantModificationDTO(
        String roomId,
        String actionPerformerId,
        PlayerDTO modifiedParticipant,
        boolean resetAllVotes
) {

}
