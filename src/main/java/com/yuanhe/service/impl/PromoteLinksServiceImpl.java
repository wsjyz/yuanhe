package com.yuanhe.service.impl;

import com.yuanhe.dao.PromoteLinksDAO;
import com.yuanhe.domain.PromoteLinks;
import com.yuanhe.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.service.PromoteLinksService;

import java.util.List;

@Service("PromoteLinksService")
public class PromoteLinksServiceImpl implements PromoteLinksService {

    @Autowired
    PromoteLinksDAO promoteLinksDAO;

    @Override
    public PromoteLinks addPromoteLink(PromoteLinks promoteLink) {
        if(StringUtils.isBlank(promoteLink.getLinkId())){
            promoteLink.setLinkId(StringUtil.genUUID());
        }
        return promoteLinksDAO.addPromoteLink(promoteLink);
    }

    @Override
    public List<PromoteLinks> findPromoteLinkList(long start, long end) {
        return promoteLinksDAO.findPromoteLinkList(start,end);
    }

    @Override
    public int findPromoteLinkCount() {
        return promoteLinksDAO.findPromoteLinkCount();
    }

    @Override
    public void deletePromoteLink(String promoteLinkId) {
        promoteLinksDAO.deletePromoteLink(promoteLinkId);
    }
}
