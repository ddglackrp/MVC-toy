package dnf.itemauction.domain.character;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class Character implements Comparable<Character> {

    private Long id;

    private String characterId;

    private String name;

    private String server;

    private Long level;

    private String job;

    private Long fame;

    private String memberName;

    //createdAt : DB 연동 할 떄 INSERT 시 시간을 저장 할 ATTRIBUTE
    //현재는 DB 연동이 되어있지 않기 때문에 JAVA DATE 사용
    private Date createdAt;


    //updatedAt : DB 연동 할 떄 마지막 UPDATE 시간을 저장 할 ATTRIBUTE
    private Date updateAt;

    public Character(){}

    @Builder
    public Character(String characterId, String name, String server, Long level, String job, Long fame, String memberName, Date createdAt, Date updateAt) {
        this.characterId = characterId;
        this.name = name;
        this.server = server;
        this.level = level;
        this.job = job;
        this.fame = fame;
        this.memberName = memberName;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }


    @Override
    public int compareTo(Character character) {
        if(fame > character.fame) return -1;
        else if(fame < character.fame) return 1;
        return 0;
    }
}
