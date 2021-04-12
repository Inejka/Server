package support;

import java.io.Serializable;

public enum Commands implements Serializable {
    setStudents,
    addStudent,
    getStudent,
    getStudentsCount,
    removeStudent,
    searchStudentSurnameLimits,
    searchStudentGroup,
    searchStudentSurname,
    searchStudentGroupLimits,
    removeStudents,
    getGroupsId,
    getStudents,
    close,
    getFiles,
    save,
    load,
    closeTab,
    pop
}
