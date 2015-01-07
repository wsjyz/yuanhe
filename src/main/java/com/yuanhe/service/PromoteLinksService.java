package com.yuanhe.service;

import com.yuanhe.domain.PromoteLinks;

import java.util.List;

public interface PromoteLinksService {

    PromoteLinks addPromoteLink(PromoteLinks promoteLink);

    List<PromoteLinks> findPromoteLinkList(long start, long end);

    List<PromoteLinks> findPromoteLinkList();

    int findPromoteLinkCount();

    void deletePromoteLink(String promoteLinkId);

    PromoteLinks findPromoteLinkById(String linkId);
}
