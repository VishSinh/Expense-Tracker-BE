package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
}
