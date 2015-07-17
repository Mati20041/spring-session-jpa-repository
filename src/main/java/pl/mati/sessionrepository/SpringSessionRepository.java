package pl.mati.sessionrepository;


import org.springframework.data.repository.CrudRepository;

public interface SpringSessionRepository extends CrudRepository<SessionEntity, String>{
}
