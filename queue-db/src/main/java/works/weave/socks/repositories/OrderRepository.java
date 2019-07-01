package works.weave.socks.repositories;

import works.weave.socks.sqlrepo.entities.CustomerOrder;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.List;	

@Repository
public interface OrderRepository extends CrudRepository<CustomerOrder, String> {
	List<CustomerOrder> findById(String id);
}