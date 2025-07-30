package tk.therealsuji.vtopchennai.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tk.therealsuji.vtopchennai.R;
import tk.therealsuji.vtopchennai.adapters.GPACourseAdapter;
import tk.therealsuji.vtopchennai.helpers.SettingsRepository;
import tk.therealsuji.vtopchennai.models.GPACourse;

public class GPACalculatorFragment extends Fragment implements GPACourseAdapter.OnCourseDeleteListener {
    private TextInputEditText creditHoursInput;
    private AutoCompleteTextView gradeDropdown;
    private MaterialButton addCourseButton;
    private MaterialButton calculateGPAButton;
    private MaterialButton clearAllButton;
    private MaterialButton calculateCGPAButton;
    private RecyclerView coursesRecyclerView;
    private TextView noCoursesText;
    private TextView creditsBeingAddedText;
    private TextInputEditText currentCGPAInput;
    private TextInputEditText currentCreditsInput;
    private TextView cgpaResultText;

    private GPACourseAdapter courseAdapter;
    private List<GPACourse> courses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gpa_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initializeViews(view);
        setupRecyclerView();
        setupGradeDropdown();
        setupClickListeners();
        autoFillCurrentData();
        updateUI();
    }

    private void initializeViews(View view) {
        creditHoursInput = view.findViewById(R.id.credit_hours_input);
        gradeDropdown = view.findViewById(R.id.grade_dropdown);
        addCourseButton = view.findViewById(R.id.add_course_button);
        calculateGPAButton = view.findViewById(R.id.calculate_gpa_button);
        clearAllButton = view.findViewById(R.id.clear_all_button);
        calculateCGPAButton = view.findViewById(R.id.calculate_cgpa_button);
        coursesRecyclerView = view.findViewById(R.id.courses_recycler_view);
        noCoursesText = view.findViewById(R.id.no_courses_text);
        creditsBeingAddedText = view.findViewById(R.id.credits_being_added_text);
        currentCGPAInput = view.findViewById(R.id.current_cgpa_input);
        currentCreditsInput = view.findViewById(R.id.current_credits_input);
        cgpaResultText = view.findViewById(R.id.cgpa_result_text);

        courses = new ArrayList<>();
    }

    private void setupRecyclerView() {
        courseAdapter = new GPACourseAdapter();
        courseAdapter.setOnCourseDeleteListener(this);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        coursesRecyclerView.setAdapter(courseAdapter);
    }

    private void setupGradeDropdown() {
        String[] grades = GPACourse.getAvailableGrades();
        String[] gradeDisplayTexts = new String[grades.length];
        
        for (int i = 0; i < grades.length; i++) {
            gradeDisplayTexts[i] = GPACourse.getGradeDisplayText(grades[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                gradeDisplayTexts
        );
        gradeDropdown.setAdapter(adapter);
    }

    private void setupClickListeners() {
        addCourseButton.setOnClickListener(v -> addCourse());
        calculateGPAButton.setOnClickListener(v -> calculateGPA());
        clearAllButton.setOnClickListener(v -> clearAll());
        calculateCGPAButton.setOnClickListener(v -> calculateCGPA());
        updateCreditsDisplay();
    }

    private void autoFillCurrentData() {
        SharedPreferences sharedPreferences = SettingsRepository.getSharedPreferences(requireContext());
        
        // Auto-fill current CGPA
        float currentCGPA = sharedPreferences.getFloat("cgpa", 0);
        if (currentCGPA > 0) {
            currentCGPAInput.setText(String.format("%.2f", currentCGPA));
        }
        
        // Auto-fill current credits
        float totalCredits;
        try {
            // Support old integer based credits
            totalCredits = sharedPreferences.getInt("totalCredits", 0);
        } catch (Exception ignored) {
            totalCredits = sharedPreferences.getFloat("totalCredits", 0);
        }
        
        if (totalCredits > 0) {
            currentCreditsInput.setText(String.format("%.1f", totalCredits));
        }
    }

    private void addCourse() {
        String creditHoursStr = creditHoursInput.getText() != null ? creditHoursInput.getText().toString().trim() : "";
        String grade = gradeDropdown.getText() != null ? gradeDropdown.getText().toString().trim() : "";

        // Validate inputs
        if (TextUtils.isEmpty(creditHoursStr)) {
            creditHoursInput.setError("Credit hours is required");
            return;
        }

        double creditHours;
        try {
            creditHours = Double.parseDouble(creditHoursStr);
            if (creditHours <= 0) {
                creditHoursInput.setError("Credit hours must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            creditHoursInput.setError("Invalid credit hours");
            return;
        }

        if (TextUtils.isEmpty(grade)) {
            gradeDropdown.setError("Grade is required");
            return;
        }

        // Extract grade from display text (e.g., "A (9.0)" -> "A")
        String actualGrade = grade.split(" ")[0];

        // Create and add course
        GPACourse course = new GPACourse(creditHours, actualGrade);
        courseAdapter.addCourse(course);
        courses.add(course);

        // Clear inputs
        creditHoursInput.setText("");
        gradeDropdown.setText("");

        updateUI();
        updateCreditsDisplay();
    }

    private void calculateGPA() {
        if (courses.isEmpty()) {
            Toast.makeText(requireContext(), R.string.add_at_least_one_course, Toast.LENGTH_SHORT).show();
            return;
        }

        double totalGradePoints = 0;
        double totalCredits = 0;

        for (GPACourse course : courses) {
            totalGradePoints += course.getCourseGPA();
            totalCredits += course.getCreditHours();
        }

        double semesterGPA = totalCredits > 0 ? totalGradePoints / totalCredits : 0;

        // Show result in a toast
        DecimalFormat df = new DecimalFormat("#.##");
        String result = "Semester GPA: " + df.format(semesterGPA) + "\nTotal Credits: " + (int) totalCredits;
        Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show();
    }

    private void clearAll() {
        courses.clear();
        courseAdapter.clearCourses();
        updateUI();
        updateCreditsDisplay();
        
        // Clear result texts
        cgpaResultText.setVisibility(View.GONE);
    }

    private void calculateCGPA() {
        String currentCGPAStr = currentCGPAInput.getText() != null ? currentCGPAInput.getText().toString().trim() : "";
        String currentCreditsStr = currentCreditsInput.getText() != null ? currentCreditsInput.getText().toString().trim() : "";

        if (TextUtils.isEmpty(currentCGPAStr) || TextUtils.isEmpty(currentCreditsStr)) {
            Toast.makeText(requireContext(), R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double currentCGPA = Double.parseDouble(currentCGPAStr);
            double currentCredits = Double.parseDouble(currentCreditsStr);

            if (currentCGPA < 0 || currentCGPA > 10 || currentCredits <= 0) {
                Toast.makeText(requireContext(), R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate new semester GPA
            double semesterGPA = 0;
            double semesterCredits = 0;

            for (GPACourse course : courses) {
                semesterGPA += course.getCourseGPA();
                semesterCredits += course.getCreditHours();
            }

            if (semesterCredits > 0) {
                semesterGPA = semesterGPA / semesterCredits;
            }

            // Calculate new CGPA using the formula: (Current CGPA * Current Credits + Semester GPA * Semester Credits) / (Current Credits + Semester Credits)
            double newCGPA = ((currentCGPA * currentCredits) + (semesterGPA * semesterCredits)) / (currentCredits + semesterCredits);

            DecimalFormat df = new DecimalFormat("#.##");
            cgpaResultText.setText(getString(R.string.new_cgpa, df.format(newCGPA)));
            cgpaResultText.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        if (courses.isEmpty()) {
            noCoursesText.setVisibility(View.VISIBLE);
            coursesRecyclerView.setVisibility(View.GONE);
        } else {
            noCoursesText.setVisibility(View.GONE);
            coursesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateCreditsDisplay() {
        double totalCredits = 0;
        for (GPACourse course : courses) {
            totalCredits += course.getCreditHours();
        }
        
        if (totalCredits > 0) {
            creditsBeingAddedText.setText(getString(R.string.credits_being_added, totalCredits));
            creditsBeingAddedText.setVisibility(View.VISIBLE);
        } else {
            creditsBeingAddedText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCourseDelete(int position) {
        courses.remove(position);
        courseAdapter.removeCourse(position);
        updateUI();
        updateCreditsDisplay();
    }
} 