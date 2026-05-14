package com.example.cryoguard.evaluation.domain.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAlertsByContainerQuery {
    private Long containerId;
}