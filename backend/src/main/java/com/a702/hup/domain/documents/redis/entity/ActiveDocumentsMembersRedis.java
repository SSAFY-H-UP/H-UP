package com.a702.hup.domain.documents.redis.entity;

import com.a702.hup.application.data.dto.DocumentsMemberInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@RedisHash(value = "documentsMembers")
public class ActiveDocumentsMembersRedis {

    @Id
    @NonNull
    private String documentsId;
    private List<DocumentsMemberInfo> memberInfoList = new ArrayList<>();

    public void addMember(DocumentsMemberInfo memberDto){
        memberInfoList.add(memberDto);
    }

    public void removeMember(DocumentsMemberInfo memberDto){
        memberInfoList.remove(memberDto);
    }

    public boolean isDocumentMemberEmpty() {
        return memberInfoList.isEmpty();
    }

}
