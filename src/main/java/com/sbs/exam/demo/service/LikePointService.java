package com.sbs.exam.demo.service;

import com.sbs.exam.demo.repository.LikePointRepository;

import org.springframework.stereotype.Service;

@Service
public class LikePointService {

    private LikePointRepository likePointRepository;

    public LikePointService(LikePointRepository likePointRepository) {
        this.likePointRepository = likePointRepository;
    }

    public void doIncreaseLikePoint(int articleId, int memberId) {
        likePointRepository.doIncreaseLikePoint(articleId, memberId);
    }

    public void doDecreaseLikePoint(int articleId, int memberId) {
        likePointRepository.doDecreaseLikePoint(articleId, memberId);
    }

}
