package com.soodagram.soodagram.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="tbl_feed")
@EqualsAndHashCode(of= {"feedNo", "writer"})
public class Feed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long feedNo;

	@ManyToOne(targetEntity=Account.class, cascade = CascadeType.ALL)
	@JoinColumn(name="userSeq")
	private Account writer;

	@Column(columnDefinition="TEXT")
	private String content;

	@OneToMany(mappedBy="feed")
	@Builder.Default
	private List<LikeFeed> likeAccounts = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="feed", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<FeedFile> files = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<FeedHashtag> feedHashtags = new ArrayList<>();
	
	@OneToMany(mappedBy="feed")
	@Builder.Default
	private List<Reply> replies = new ArrayList<>();
	
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;	
	
	@Override
	public String toString() {
		return "Feed{" +
				"feedNo" + feedNo +
				", writer=" + writer +
				", content=" + content + 
				", files size=" + files.size() + 
				", feedHashtags size=" + feedHashtags.size() + 
				", replies size=" + replies.size() + 
				", likes size=" + likeAccounts.size() + 
				", regDate=" + regDate + 
				", updateDate=" + updateDate + "}";
	}
	
	public void writeFeed(Account writer) {
		this.writer = writer;
		writer.getWrittenFeed().add(this);
	}
	
	public void removeFeed(Account writer) {
		writer.getWrittenFeed().remove(this);
	}
	
	public void addHashtag(FeedHashtag feedHashtag) {
		feedHashtags.add(feedHashtag);
		feedHashtag.getHashtag().getFeedHashtag().add(feedHashtag);
	}
	
	public void removeHashtag(Hashtag hashtag) {
		for(Iterator<FeedHashtag> iterator = feedHashtags.iterator();
				iterator.hasNext(); ) {
			FeedHashtag feedHashtag = iterator.next();
			
			if(feedHashtag.getFeed().equals(this) && feedHashtag.getHashtag().equals(hashtag)) {
				iterator.remove();
				feedHashtag.getHashtag().getFeedHashtag().remove(feedHashtag);
				feedHashtag.setFeed(null);
				feedHashtag.setHashtag(null);
			}
		}
	}
	
	
}
