package com.playground.api.vote.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
	private final VoteRepository voteRepository;
	private final ModelMapper modelMapper;

	public Page<VoteResponse> getVotePageList(VoteRequest reqData, Pageable pageable) {
	
		return null;
	}

	public VoteResponse getVoteDetail(VoteRequest reqData) {
		return null;
	}

	public VoteResponse addVote(VoteRequest reqData) {
		
		
		
		String tempExcuteResult = "1";
		return VoteResponse.builder()
				.excuteResult(tempExcuteResult)
				.build();
	}

	public VoteResponse modifyVote(VoteRequest reqData) {
		
		
		
		String tempExcuteResult = "1";
		return VoteResponse.builder()
				.excuteResult(tempExcuteResult)
				.build();
	}

	public VoteResponse removeVote(VoteRequest reqData) {
		
		
		
		String tempExcuteResult = "1";
		return VoteResponse.builder()
				.excuteResult(tempExcuteResult)
				.build();
	}
}
