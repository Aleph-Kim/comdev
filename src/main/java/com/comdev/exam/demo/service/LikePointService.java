package com.comdev.exam.demo.service;

import com.comdev.exam.demo.repository.LikePointRepository;

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

    public boolean actorCanMakeLikePoint(int actorId, int id) {
		return likePointRepository.getLikePointByMemberId(id, actorId) != 0;
	}

}
