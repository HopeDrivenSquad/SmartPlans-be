package eu.profinit.smartplans.api;

import lombok.Data;

import java.util.List;

@Data
public class TransactionOverview {

    OverviewSummary summary;
    List<OverviewCategory> categories;
}
