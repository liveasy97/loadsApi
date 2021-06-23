package com.TruckBooking.TruckBooking;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.UnitValue;


@DataJpaTest
public class TestLoadDao {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LoadDao loadDao;
	
	@Test
	public void testFindbyLoadId()
	{
		Load load = createLoad();
		Load savedindb = entityManager.persist(load);
		Load getfromdb = loadDao.findByLoadId(savedindb.getLoadId()).get();

		assertThat(getfromdb).isEqualTo(savedindb);
	}
	
	@Test
	public void findBypostloadid()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = (Pageable) PageRequest.of(0, CommonConstants.pagesize);
		Iterable<Load> allLoads = loadDao.findByPostLoadId("id:1", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	public void findByLoadAndUnloadPoint()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointCityAndUnloadingPointCity("Nagpur", "Raipur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(3);
		
	}
	
	@Test
	public void findByTruckType()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByTruckType("OPEN_HALF_BODY", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	public void findbyloadid()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Optional<Load> load = loadDao.findByLoadId("loadid:2");

		assertThat(loads.get(1)).isEqualTo(load.get());
	}
	
	@Test
	public void findByLoadDate()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, 5);
		
		Iterable<Load> allLoads = loadDao.findByLoadDate("22/01/21", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(1);
	}
	
	//findByLoadingPointCity
	@Test
	public void findByLoadingPointCity()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, 5);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointCity("Nagpur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByLoadingPointState
	@Test
	public void findByLoadingPointState()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, 5);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointState("Maharashtra", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByUnloadingPointCity
	@Test
	public void findByUnloadingPointCity()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, 5);
		
		Iterable<Load> allLoads = loadDao.findByUnloadingPointCity("Raipur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByUnloadingPointState
	@Test
	public void findByUnloadingPointState()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage;
		currentPage = PageRequest.of(0, 5);
		
		Iterable<Load> allLoads = loadDao.findByUnloadingPointState("Chhattisgarh", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	@Test
	public void testUpdate() {
		
		List<Load> loads = createLoads();
		entityManager.persist(loads.get(0));
		entityManager.persist(loads.get(1));
		entityManager.persist(loads.get(2));
		
		Load getFromDb = loadDao.findByLoadId("loadid:1").get();

		getFromDb.setWeight("999kg");

		entityManager.persist(getFromDb);
		
		Load getFromDb1 = loadDao.findByLoadId("loadid:1").get();
		assertThat(getFromDb1.getWeight()).isEqualTo(getFromDb.getWeight());
	}

	@Test
	public void testDelete() {

		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));

		entityManager.remove(savedindb2);

		Iterable<Load> allTrucks = loadDao.findAll();
		List<Load> list = new ArrayList<>();

		for (Load t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}
	public Load createLoad()
	{
		Load load = new Load();
		load.setLoadId("loadid:1");
		load.setLoadingPoint("Nagpur");
		load.setLoadingPointCity("Nagpur");
		load.setLoadingPointState("Maharashtra");
		load.setPostLoadId("id:1");
		load.setUnloadingPoint("Raipur");
		load.setUnloadingPointCity("Raipur");
		load.setUnloadingPointState("Chhattisgarh");
		load.setProductType("Metal Scrap");
		load.setTruckType("OPEN_HALF_BODY");
		load.setNoOfTrucks("4");
		load.setWeight("10000kg");
		load.setComment("no comments");
		load.setStatus("pending");
		load.setLoadDate("01/01/21");
		load.setRate((long) 505500);
		load.setUnitValue(UnitValue.PER_TON);
		return load;
	}
	public List<Load> createLoads()
	{
		
		Load load1 = new Load();
		Load load2 = new Load();
		Load load3 = new Load();
		
		load1.setLoadId("loadid:1");
		load1.setLoadingPoint("Nagpur");
		load1.setLoadingPointCity("Nagpur");
		load1.setLoadingPointState("Maharashtra");
		load1.setPostLoadId("id:1");
		load1.setUnloadingPoint("Raipur");
		load1.setUnloadingPointCity("Raipur");
		load1.setUnloadingPointState("Chhattisgarh");
		load1.setProductType("Metal Scrap");
		load1.setTruckType("OPEN_HALF_BODY");
		load1.setNoOfTrucks("4");
		load1.setWeight("10000kg");
		load1.setComment("no comments");
		load1.setStatus("pending");
		load1.setLoadDate("22/01/21");
		load1.setRate((long) 505500);
		load1.setUnitValue(UnitValue.PER_TON);
		
		
		load2.setLoadId("loadid:2");
		load2.setLoadingPoint("Nagpur");
		load2.setLoadingPointCity("Nagpur");
		load2.setLoadingPointState("Maharashtra");
		load2.setPostLoadId("id:1");
		load2.setUnloadingPoint("Raipur");
		load2.setUnloadingPointCity("Raipur");
		load2.setUnloadingPointState("Chhattisgarh");
		load2.setProductType("Metal Scrap");
		load2.setTruckType("STANDARD_CONTAINER");
		load2.setNoOfTrucks("4");
		load2.setWeight("10000kg");
		load2.setComment("no comments");
		load2.setStatus("pending");
		load2.setLoadDate("01/01/21");
		load2.setRate((long) 505500);
		load2.setUnitValue(UnitValue.PER_TON);
		
		load3.setLoadId("loadid:3");
		load3.setLoadingPoint("Nagpur");
		load3.setLoadingPointCity("Nagpur");
		load3.setLoadingPointState("Maharashtra");
		load3.setPostLoadId("id:2");
		load3.setUnloadingPoint("Raipur");
		load3.setUnloadingPointCity("Raipur");
		load3.setUnloadingPointState("Chhattisgarh");
		load3.setProductType("Metal Scrap");
		load3.setTruckType("OPEN_HALF_BODY");
		load3.setNoOfTrucks("4");
		load3.setWeight("10000kg");
		load3.setComment("no comments");
		load3.setStatus("pending");
		load3.setLoadDate("01/01/21");
		load3.setRate((long) 505500);
		load3.setUnitValue(UnitValue.PER_TON);
		List<Load> loads = Arrays.asList(load1, load2, load3);
		
		return loads;
	}
}


