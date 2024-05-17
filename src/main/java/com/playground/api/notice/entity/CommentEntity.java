package com.playground.api.notice.entity;

import java.util.ArrayList;
import java.util.List;
import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "tb_comment")
@IdClass(CommentEntityPK.class)
@SequenceGenerator(
	    name = "cmnt_no_seq",
	      sequenceName = "tb_comment_cmnt_no_seq",
	      initialValue = 1,
	      allocationSize = 1
	  )
public class CommentEntity extends BaseEntity{
	
  @Id
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name="bbs_id",referencedColumnName = "bbs_id"),
    @JoinColumn(name="ntt_no",referencedColumnName = "ntt_no")
        })
  private PostEntity postEntity;
  
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cmnt_no_seq")
	@Column(name = "cmnt_no")
	private Integer cmntNo;
	
	@Column(name = "cmnt_cn")
	private String cmntCn;
	
	@Column(name = "upper_cmnt_no")
	private Integer upperCmntNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "upper_cmnt_no", referencedColumnName ="upper_cmnt_no", insertable = false, updatable = false)
  private CommentEntity parent;
	
	@Builder.Default
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true)
  private List<CommentEntity> children = new ArrayList<>();
	
}
