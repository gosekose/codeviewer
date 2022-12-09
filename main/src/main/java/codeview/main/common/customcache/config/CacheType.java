package codeview.main.common.customcache.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {

    /**
     *  클라이언트가 새로운 그룹을 생성하면, 캐시를 제거한 후, 새로운 데이터 받기
     *
     */

    MEMBER("member", 5 * 60, 10000),
    MEMBERPROFILE("memberProfile", 5*60, 10000),
    MEMBERGROUP("memberGroup", 5 * 60, 10000),
    PROBLEMSCORE("problemScore", 5 * 60, 10000),


    GROUPJOINREQUEST("groupJoinRequest", 5 * 60, 10000),


    GROUPSEARCH("groupSearch", 5 * 60, 10000),
    MYGROUPSEARCH("myGroupSearch", 5 * 60, 10000),

    PROBLEM("problem", 5 * 60, 10000),
    PROBLEMFORBOARD("problemForBoard", 5*60, 10000),
    PROBLEMDESCRIPTION("problemDescription", 5 * 60, 10000),
    PROBLEMIOEXAMPLE("problemIoExample", 5 * 60, 10000),

    MYGROUPMEMBERINFO("myMemberGroupInfo", 5 * 60, 10000),
    MYGROUPMEMBERSOLVENOPROBLEMID("myGroupMemberSolveNoProblemId", 5 * 60, 10000),
    MYGROUPMEMBERSOLVEINFO("myGroupMemberSolveInfo", 5 * 60, 10000),

    MYSOLVECHART("mySolveChart", 5 * 60, 10000),
    OTHERSOLVECHART("otherSolveChart", 5 * 60, 10000),

    JOINREQUEST("joinRequest", 5 * 60, 10000),



    PROBLEMSEARCH("problemSearch", 5 * 60, 10000),
    MYPROBLEMSEARCH("myProblemSearch", 5 * 60, 10000),
    MEMBERAllSOLVESEARCH("memberAllSolveSearch", 30, 10000),
    MEMBERSOLVESEARCH("memberSolveSearch", 30, 10000);

    private final String cacheName;     // 캐시 이름
    private final int expireAfterWrite; // 만료시간
    private final int maximumSize;      // 최대 갯수


}
