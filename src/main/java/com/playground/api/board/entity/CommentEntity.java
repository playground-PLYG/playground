package com.playground.api.board.entity;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
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
import lombok.ToString;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@DynamicInsert
@Table(name = "tb_comment")
@IdClass(CommentEntityPK.class)
@SequenceGenerator(name = "tb_comment_cmnt_no_seq", sequenceName = "tb_comment_cmnt_no_seq", initialValue = 1, allocationSize = 1)
@ToString(exclude = {"parent", "children"})
public class CommentEntity extends BaseEntity {

  @Id
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "bbs_id", referencedColumnName = "bbs_id"), @JoinColumn(name = "ntt_sn", referencedColumnName = "ntt_sn")})
  private PostEntity postEntity;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_comment_cmnt_no_seq")
  @Column(name = "cmnt_sn")
  private Integer cmntSn;

  @Column(name = "cmnt_cn")
  private String cmntCn;

  @Column(name = "upper_cmnt_sn")
  private Integer upperCmntSn;

  @Column(name = "delete_at")
  private String deleteAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({@JoinColumn(name = "bbs_id", referencedColumnName = "bbs_id", insertable = false, updatable = false),
      @JoinColumn(name = "ntt_sn", referencedColumnName = "ntt_sn", insertable = false, updatable = false),
      @JoinColumn(name = "upper_cmnt_sn", referencedColumnName = "cmnt_sn", insertable = false, updatable = false)})
  private CommentEntity parent;

  @Builder.Default
  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<CommentEntity> children = new ArrayList<>();

}
