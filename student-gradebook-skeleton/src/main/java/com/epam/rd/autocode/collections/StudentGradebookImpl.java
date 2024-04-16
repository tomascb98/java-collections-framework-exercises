package com.epam.rd.autocode.collections;

import java.math.BigDecimal;
import java.util.*;


public class StudentGradebookImpl implements StudentGradebook {

	private Map<Student, Map<String, BigDecimal>> map;

	public StudentGradebookImpl() {
		this.map = new TreeMap<>(getComparator());
	}

	@Override
	public boolean addEntryOfStudent(Student student, String discipline, BigDecimal grade) {
		if(map.containsKey(student)){
			Map<String, BigDecimal> currentStudent = map.get(student);
			if(currentStudent.containsKey(discipline) && currentStudent.get(discipline).equals(grade)){
				return false;
			} else {
				currentStudent.put(discipline, grade);
				return true;
			}
		} else{
			map.put(student, new TreeMap<>(Map.of(discipline, grade)));
			return map.containsKey(student);
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Comparator<Student> getComparator() {
		return new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				int comp = o1.getLastName().compareTo(o2.getLastName());
				if(comp == 0) comp = o1.getFirstName().compareTo(o2.getFirstName());
				if(comp == 0) comp = o1.getGroup().compareTo(o2.getGroup());
				return comp;
			}
		};
	}

	@Override
	public List<String> getStudentsByDiscipline(String discipline) {
		List<String> studentsByDiscipline = new ArrayList<>();
		Set<Map.Entry<Student, Map<String, BigDecimal>>> setMap = map.entrySet();
		for (Map.Entry<Student, Map<String, BigDecimal>> student : setMap) {
			if(student.getValue().containsKey(discipline)){
				studentsByDiscipline.add(toStringByDiscipline(student.getKey(), student.getValue().get(discipline)));
			}
		}
		return studentsByDiscipline;
	}

	private String toStringByDiscipline(Student student, BigDecimal grade){
        return student.getLastName() +
				"_" +
				student.getFirstName() +
				": " +
				grade.toPlainString();
	}

	@Override
	public Map<Student, Map<String, BigDecimal>> removeStudentsByGrade(BigDecimal grade) {
		Map<Student, Map<String, BigDecimal>> studentsRemoved = new TreeMap<>(getComparator());
		Set<Map.Entry<Student, Map<String, BigDecimal>>> setMap = map.entrySet();
		for (Map.Entry<Student, Map<String, BigDecimal>> student : setMap) {
			List<BigDecimal> grades = new ArrayList<>(student.getValue().values());
			for (BigDecimal gradeByDiscipline : grades) {
				if(gradeByDiscipline.compareTo(grade) < 0){
					studentsRemoved.put(student.getKey(), student.getValue());
				}
			}
		}
		removeStudents(studentsRemoved.keySet());
		return studentsRemoved;
	}
	private void removeStudents(Set<Student> studentsToRemove){
		for (Student student:
			 studentsToRemove) {
			map.remove(student);
		}
	}

	@Override
	public Map<BigDecimal, List<Student>> getAndSortAllStudents() {
		Map<BigDecimal, List<Student>> studentsSortedByAvgGrade = new TreeMap<>(new Comparator<BigDecimal>() {
			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				return o1.compareTo(o2);
			}
		});

		Set<Map.Entry<Student, Map<String, BigDecimal>>> setMap = map.entrySet();
		for (Map.Entry<Student, Map<String, BigDecimal>> student : setMap) {
			List<BigDecimal> grades = new ArrayList<>(student.getValue().values());
			BigDecimal sumGrades = BigDecimal.ZERO;
			for (BigDecimal gradeByDiscipline : grades) {
				sumGrades = sumGrades.add(gradeByDiscipline);
			}
			addStudentByAvgGrade(studentsSortedByAvgGrade, student.getKey(), sumGrades.divide(BigDecimal.valueOf(grades.size())));
		}
		return studentsSortedByAvgGrade;
	}

	private void addStudentByAvgGrade(Map<BigDecimal, List<Student>> studentsByAvgGrade,Student student, BigDecimal avgGrade){
		if(studentsByAvgGrade.containsKey(avgGrade)){
			studentsByAvgGrade.get(avgGrade).add(student);
		} else {
			studentsByAvgGrade.put(avgGrade, new ArrayList<>(List.of(student)));
		}
	}
	
}
