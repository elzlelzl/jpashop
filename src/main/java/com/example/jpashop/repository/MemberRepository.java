package com.example.jpashop.repository;

import com.example.jpashop.domain.Member;
import com.example.jpashop.web.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public void save(Member member) {
        em.persist(member);
    }
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",
                Member.class).setParameter("name", name).getResultList();
    }

    public List<Member> loginByMember(LoginForm loginForm) {
        TypedQuery<Member> query = em.createQuery("select m from Member m " +
                        "where m.name = :name and  m.pwd = :pwd", Member.class);
        query.setParameter("name", loginForm.getName());
        query.setParameter("pwd", loginForm.getPwd());
        return  query.getResultList();
    }
}

