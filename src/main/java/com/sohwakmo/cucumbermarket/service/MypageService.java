package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MypageReadDto;
import com.sohwakmo.cucumbermarket.dto.MypageUpdateDto;
import com.sohwakmo.cucumbermarket.dto.ProfileImageReadDto;
import com.sohwakmo.cucumbermarket.repository.ChatRoomRepository;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.MypageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MemberRepository memberRepository;


    //마이페이지 사용자 정보 load
    @Transactional(readOnly = true)
    public MypageReadDto loadProfile(Integer memberNo) {
        log.info("loadProfile(memberNo={})", memberNo);
        Member entity = mypageRepository.findByMemberNo(memberNo);

        log.info("Member Return value Service: entity={}", entity);
        return MypageReadDto.fromEntity(entity);

    }

    //마이페이지 사용자 정보 수정
    @Transactional
    public Integer update(MypageUpdateDto dto) {
        log.info("update(dto={})", dto);
        Member entity = mypageRepository.findByMemberNo(dto.getMemberNo());

        entity.memberUpdate(dto.getName(), dto.getNickname(), dto.getPassword(), dto.getAddress(), dto.getPhone(), dto.getEmail());
        return dto.getMemberNo();
    }

    //마이페이지 사용자 사진 프로필 load
    @Transactional(readOnly = true)
    public ProfileImageReadDto readProfileImage(Integer memberNo) {
        log.info("readProfileImage(memberNo={})", memberNo);
        Member entity = mypageRepository.findByMemberNo(memberNo);
        return ProfileImageReadDto.fromEntity(entity);

    }

    //마이페이지 사용자 사진 수정
    @Transactional
    public Integer updateImage(ProfileImageReadDto dto) {
        log.info("updateImage(dto={}, file={})", dto);

        Member entity = mypageRepository.findByMemberNo(dto.getMemberNo());

        entity.userImageUpdate(dto.getUserImgName(),dto.getUserImgUrl());

        return entity.getMemberNo();
    }

    @Transactional(readOnly = true)
    public Double readUserTemp(Integer memberNo) {
        log.info("readUserTemp(memberNo={})", memberNo);


        Member entity = mypageRepository.findByMemberNo(memberNo);

        return entity.getGrade();
    }

    public Integer countRecievedMessage(Integer memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        List<ChatRoom> chatRooms = chatRoomRepository.findByRoomIdOrMemberMemberNo(member.getNickname(), memberNo);
        Integer recievedMessage = 0;
        for(ChatRoom c : chatRooms){
            if(!c.getLastEnterName().equals(member.getNickname()))
            recievedMessage += c.getUnReadMessages();
        }
        return recievedMessage;
    }
}
