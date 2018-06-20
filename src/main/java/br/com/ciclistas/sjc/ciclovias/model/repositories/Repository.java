package br.com.ciclistas.sjc.ciclovias.model.repositories;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 
 * Uma classe Repositorio abstrata para uso com as entidades do nosso sistema
 * 
 * @author Pedro Hos
 * @author William Siqueira
 * 
 */
@Stateless
public abstract class Repository<T> {

	protected Class<T> type = getType();

	@PersistenceContext(unitName = "ciclovias-unit")
	protected EntityManager em;

	public void save(T entidade) {
		em.persist(entidade);
	}

	public void remove(T entidade) {
		em.remove(entidade);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(type));
		return (List<T>) em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> listAllPaginated(int total, int pg) {
		CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(type));

		Query busca = em.createQuery(cq);

		busca.setFirstResult(pg * total);
		busca.setMaxResults(total);

		return (List<T>) busca.getResultList();
	}

	public T findById(long id) {
		return em.find(type, id);
	}

	public T update(T entidade) {
		return em.merge(entidade);
	}

	public long countAll() {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(type)));
		return em.createQuery(cq).getSingleResult();
	}

	/**
	 * @author Pedro Hos<br>
	 * 
	 *         Utilizando Exemplo de Eduardo Guerra!
	 *         https://groups.google.com/forum
	 *         /#!topic/projeto-oo-guiado-por-padroes/pOIiOD9cifs
	 * 
	 *         Este método retorna o tipo da Classe, dessa maneira não é
	 *         necessário cada Service expor seu tipo!!!!
	 * 
	 * @return Class<T>
	 */
	@SuppressWarnings({ "unchecked" })
	private Class<T> getType() {
		Class<?> clazz = this.getClass();

		while (!clazz.getSuperclass().equals(Repository.class)) {
			clazz = clazz.getSuperclass();
		}

		ParameterizedType genericType = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<T>) genericType.getActualTypeArguments()[0];
	}
}
