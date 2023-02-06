package com.ajd.meow.service.community;


import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class CommunityMasterService {

    @Autowired
    private CommunityMasterRepository communityMasterRepository;

    @Autowired
    private CommunityImageRepository communityImageRepository;


    //글 작성
    public void write(CommunityMaster communityMaster,CommunityImage communityImage, MultipartFile file) throws Exception{

        System.out.println("111111111111111111" + file.getOriginalFilename());

        if(file.getOriginalFilename().isEmpty()){
            communityMaster.setCommunityId("ADP_ACT");
            communityMaster.setPostId("FOOD_SELL");
            communityMaster.setCreatePostDate(LocalDateTime.now());


            communityMasterRepository.save(communityMaster);


        }else {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveImg = new File(projectPath,fileName);
            file.transferTo(saveImg);

            communityImage.setImgName(fileName);
            communityImage.setImgPath("/files/" + fileName);


            communityMaster.setCommunityId("ADP_ACT");
            communityMaster.setPostId("FOOD_SELL");
            communityMaster.setCreatePostDate(LocalDateTime.now());

            communityMasterRepository.save(communityMaster);
            communityImage.setPostNo(communityMaster.getPostNo());
            communityImageRepository.save(communityImage);

        }
        }







    // 게시글 리스트
    public Page<CommunityMaster> boardList(Pageable pageable){
        return communityMasterRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public CommunityMaster boardView(Long postNo){return communityMasterRepository.findById(postNo).get();
    }
    //게시글 삭제 하기
    public void boardDelete(Long postNo) {
        communityMasterRepository.deleteById(postNo);
    }

    //게시글 수정
    public void boardUpdate(CommunityMaster communityMaster){

        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMasterRepository.save(communityMaster);
    }

    public CommunityImage commuImg(Long postNo){return communityImageRepository.findByPostNo(postNo);}


}