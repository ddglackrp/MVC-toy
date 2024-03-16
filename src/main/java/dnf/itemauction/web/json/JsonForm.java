package dnf.itemauction.web.json;

import lombok.Data;

@Data
public class JsonForm {

    String serverId;

    String characterId;

    String characterName;

    Long level;

    String jobId;

    String jobGrowId;

    String jobName;

    String jobGrowName;

    Long fame;
}
