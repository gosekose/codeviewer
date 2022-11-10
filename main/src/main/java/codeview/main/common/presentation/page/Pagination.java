package codeview.main.common.presentation.page;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {

    /** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
    private int pageSize = 10;

    /** 2. 페이징된 버튼의 블럭당 최대 개수 **/
    private int blockSize = 10;

    /** 3. 현재 페이지 **/
    private int page = 1;

    /** 4. 현재 블럭 **/
    private int block = 1;

    /** 5. 총 게시글 수 **/
    private int totalListCnt;

    /** 6. 총 페이지 수 **/
    private int totalPageCnt;

    /** 7. 총 블럭 수 **/
    private int totalBlockCnt;

    /** 8. 블럭 시작 페이지 **/
    private int startPage = 1;

    /** 9. 블럭 마지막 페이지 **/
    private int endPage = 1;

    /** 10. DB 접근 시작 index **/
    private int startIndex = 0;

    /** 11. 이전 블럭의 마지막 페이지 **/
    private int prevBlock;

    /** 12. 다음 블럭의 시작 페이지 **/
    private int nextBlock;


    public Pagination(int totalListCnt, int page) {

        setPage(page);

        setTotalListCnt(totalListCnt);

        setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));

        setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));

        setBlock((int) Math.ceil((page * 1.0)/blockSize));

        setStartPage((block - 1) * blockSize + 1);

        setEndPage(startPage + blockSize - 1);

        if(endPage > totalPageCnt){this.endPage = totalPageCnt;}

        setPrevBlock((block * blockSize) - blockSize);

        if(prevBlock < 1) {this.prevBlock = 1;}

        setNextBlock((block * blockSize) + 1);

        if(nextBlock > totalPageCnt) {nextBlock = totalPageCnt;}

        setStartIndex((page-1) * pageSize);

    }

}