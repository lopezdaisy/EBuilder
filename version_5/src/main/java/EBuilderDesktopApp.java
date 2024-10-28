import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public final class EBuilderDesktopApp extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final UserDataModel dataModel;

    public EBuilderDesktopApp() {
        super("EBuilder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100,800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        dataModel = new UserDataModel();//initialize the data model

        ImageIcon icon = new ImageIcon("C:\\Users\\User\\Desktop\\3rd Year Project\\version_5\\src\\main\\java\\resources\\icon.jpg.png");
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        setIconImage(new ImageIcon(resizedImg).getImage());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        addComponents(dataModel);//add data model to sections
        setVisible(true);

        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if((e.getNewState() & Frame.ICONIFIED)==Frame.ICONIFIED){
                    setSize(1100, 800);
                }
            }
        });
    }

    private void addComponents(UserDataModel dataModel) {
        cardPanel.add(new HomePanel(), "HomePanel");
        cardPanel.add(new PersonalInformationSection(cardLayout, cardPanel,dataModel), "PersonalInformationSection");
        cardPanel.add(new ObjectiveSection(cardLayout, cardPanel,dataModel), "ObjectiveSection");
        cardPanel.add(new HighlightsOfQualificationsSection(cardLayout, cardPanel,dataModel), "HighlightsOfQualificationsSection");
        cardPanel.add(new ExperienceSection(cardLayout, cardPanel,dataModel), "ExperienceSection");
        cardPanel.add(new EducationSection(cardLayout, cardPanel,dataModel), "EducationSection");
        cardPanel.add(new RefereeSection(cardLayout, cardPanel,dataModel), "RefereeSection");
        cardPanel.add(new SaveAsSection(cardLayout, cardPanel,dataModel), "SaveAsSection");
        cardLayout.show(cardPanel, "HomePanel");
    }

    private class HomePanel extends JPanel {
        public HomePanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(240, 240, 240));

            JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\User\\Desktop\\3rd Year Project\\version_5\\src\\main\\java\\resources\\icon.jpg.png"));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(logoLabel, BorderLayout.NORTH);

            JLabel welcomeLabel = new JLabel("<html><center><div style='font-size:24pt; font-weight:bold;'>Welcome to EBuilder Application!</div>" +
                    "<div style='font-size:16pt;'>A new modern and quicker way to transform your Resume into a creative design</div>" +
                    "<div style='font-size:16pt;'>using our Resume Builder Application</div></center></html>");
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            welcomeLabel.setForeground(new Color(70, 70, 70)); // Dark gray color
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            add(welcomeLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(new Color(240, 240, 240));

            JButton newResumeButton = new JButton("Create New Resume");
            newResumeButton.setBackground(new Color(0, 120, 215));
            newResumeButton.setForeground(Color.WHITE);
            newResumeButton.setFont(new Font("Segoe UI", Font.BOLD, 18));

            newResumeButton.addActionListener(e -> cardLayout.show(cardPanel, "PersonalInformationSection"));

            buttonPanel.add(newResumeButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.showSplash();

        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(50);
                splashScreen.setProgress(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashScreen.dispose();

        SwingUtilities.invokeLater(EBuilderDesktopApp::new);
    }
}

final class SplashScreen extends JWindow {
    private final JProgressBar progressBar;

    SplashScreen() {
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(25,25,112));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy++;
        JLabel eBuilderLabel = new JLabel("<html><div style='font-size:30pt; font-weight:bold; color:white;'>EBuilder</div>" +
                "<div style='font-size:16pt; color:white;'>Community 2024.0.1</div></html>");
        content.add(eBuilderLabel, gbc);

        gbc.gridy++;
        JLabel loadingLabel = new JLabel("<html><div style='font-size:16pt; color:white;'>Loading......</div></html>");
        content.add(loadingLabel, gbc);

        // Progress Bar
        gbc.gridx++;
        gbc.weighty = 0.5;
        content.add(progressBar, gbc);

        setContentPane(content);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    void showSplash() {
        setVisible(true);
    }

    void setProgress(int progress) {
        progressBar.setValue(progress);
        progressBar.setBackground(new Color(25,25,112));
        progressBar.setForeground(new Color(25,25,112));
        progressBar.setBorderPainted(false);
        progressBar.setString(progress + "%");
    }
}

final class UserDataModel {
    public PersonalInformation personalInfo;
    public ObjectivesInformation objectivesInfo;
    public HighlightsOfQualificationsInfo highlightsInfo;
    public ExperienceInformation experiencesInfo;
    public EducationInformation educationInfo;
    public RefereeInformation refereeInfo;

    public UserDataModel() {
        personalInfo = new PersonalInformation();
        objectivesInfo = new ObjectivesInformation();
        highlightsInfo = new HighlightsOfQualificationsInfo();
        experiencesInfo = new ExperienceInformation();
        educationInfo = new EducationInformation();
        refereeInfo = new RefereeInformation();
    }
}

final class PersonalInformation {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String gender;

    public PersonalInformation() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

final class PersonalInformationSection extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField phoneField;
    private final JTextField emailField;
    private final JRadioButton maleRadioButton;
    private final JRadioButton femaleRadioButton;
    private final ButtonGroup genderGroup;
    private final UserDataModel dataModel;

    public PersonalInformationSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.dataModel = dataModel;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel headingLabel = new JLabel("Personal Information Section");
        headingLabel.setForeground(new Color(0, 120, 215));
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headingLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        JLabel firstNameLabel = new JLabel("First Name:");
        add(firstNameLabel, gbc);

        gbc.gridx++;
        firstNameField = new JTextField(20);
        setPlaceholder(firstNameField, "Enter your first name");
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lastNameLabel = new JLabel("Last Name:");
        add(lastNameLabel, gbc);

        gbc.gridx++;
        lastNameField = new JTextField(20);
        setPlaceholder(lastNameField, "Enter your last name");
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel addressLabel = new JLabel("Email Address:");
        add(addressLabel, gbc);

        gbc.gridx++;
        emailField = new JTextField(20);
        setPlaceholder(emailField, "Enter your email address");
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone Number:");
        add(phoneLabel, gbc);

        gbc.gridx++;
        phoneField = new JTextField(20);
        setPlaceholder(phoneField, "Enter your phone number");
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel genderLabel = new JLabel("Gender:");
        add(genderLabel, gbc);

        gbc.gridx++;
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        JPanel genderPanel = new JPanel();
        genderPanel.setOpaque(false);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        add(genderPanel, gbc);

        gbc.gridx++;
        gbc.gridy++;
        JButton nextButton = new JButton("Next: Objectives");
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        add(nextButton, gbc);

        phoneField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePhone();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validatePhone();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validatePhone();
            }
        });

        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEmail();
            }
        });

        nextButton.addActionListener(e -> {
            if (validateFields()) {
                cardLayout.show(cardPanel, "ObjectiveSection");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all mandatory fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void validatePhone() {
        String phoneRegex = "^(07|01|\\+2547|\\+2541)\\d{8}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneField.getText());
        phoneField.setForeground(matcher.matches() ? Color.BLACK : Color.RED);
    }

    private void validateEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailField.getText());
        emailField.setForeground(matcher.matches() ? Color.BLACK : Color.RED);
    }

    private boolean validateFields() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validate first name and last name
        if (!firstName.matches("[a-zA-Z]+")) {
            // Display error message for invalid first name
            JOptionPane.showMessageDialog(this, "First name should contain only characters", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            // Display error message for invalid last name
            JOptionPane.showMessageDialog(this, "Last name should contain only characters", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Set the validated values in the data model
        dataModel.personalInfo.setFirstName(firstName);
        dataModel.personalInfo.setLastName(lastName);
        dataModel.personalInfo.setEmail(email);
        dataModel.personalInfo.setPhone(phone);

        if (maleRadioButton.isSelected()) {
            dataModel.personalInfo.setGender("Male");
        } else if (femaleRadioButton.isSelected()) {
            dataModel.personalInfo.setGender("Female");
        }

        return !firstName.isEmpty() && !email.isEmpty() &&
                phoneField.getForeground() != Color.RED &&
                emailField.getForeground() != Color.RED;
    }
}

final class ObjectivesInformation {
    private String objectives;

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }
}

final class ObjectiveSection extends JPanel {
    private final UserDataModel dataModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public ObjectiveSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.dataModel = dataModel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel sectionLabel = new JLabel("Objectives Section");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionLabel.setForeground(new Color(0, 120, 215));
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(sectionLabel, BorderLayout.NORTH);

        JTextPane objectiveTextPane = new JTextPane();
        objectiveTextPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        objectiveTextPane.setBackground(Color.WHITE);
        objectiveTextPane.setForeground(new Color(74, 74, 74));
        JScrollPane scrollPane = new JScrollPane(objectiveTextPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JLabel tooltipLabel = new JLabel();
        tooltipLabel.setText("<html><p style='width: 200px;'>Enter your career objectives here.</p></html>");
        objectiveTextPane.setToolTipText("Enter your career objectives here.");
        objectiveTextPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) {
                Point p = evt.getPoint();
                try {
                    Rectangle r = objectiveTextPane.modelToView(objectiveTextPane.viewToModel(p));
                    if (r != null) {
                        tooltipLabel.setBounds(r.x, r.y + r.height, r.width, 30);
                        tooltipLabel.setVisible(true);
                    }
                } catch (BadLocationException ex) {
                    tooltipLabel.setVisible(false);
                }
            }
        });
        add(tooltipLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton nextButton = createButton("Next: Skills", new Color(0, 120, 215));

        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "HighlightsOfQualificationsSection");
            dataModel.objectivesInfo.setObjectives(objectiveTextPane.getText());
        });

    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        return button;
    }
}

final class HighlightsOfQualificationsInfo {
    private final List<String> qualificationsList = new ArrayList<>();

    public List<String> getQualificationsList() {
        return qualificationsList;
    }

    public void addQualification(String qualification) {
        qualificationsList.add(qualification);
    }

    public void removeQualification(String qualification) {
        qualificationsList.remove(qualification);
    }
}

class HighlightsOfQualificationsSection extends JPanel {
    private final UserDataModel dataModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public HighlightsOfQualificationsSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.dataModel = dataModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        DefaultListModel<String> qualificationsModel = new DefaultListModel<>();
        JList<String> qualificationsList = new JList<>(qualificationsModel);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Skills Section");
        titledBorder.setTitleColor(new Color(0, 120, 215));
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD, 16));
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        JScrollPane qualificationsScrollPane = new JScrollPane(qualificationsList);
        qualificationsScrollPane.setBorder(titledBorder);
        add(qualificationsScrollPane, BorderLayout.CENTER);

        JButton addButton = createButton("Add Skill", new Color(0, 120, 215));
        JButton removeButton = createButton("Remove Skill", new Color(255, 0, 0));

        qualificationsList.setBackground(new Color(240, 240, 240));
        qualificationsList.setSelectionBackground(new Color(249, 247, 167));

        addButton.addActionListener(e -> addQualification(qualificationsModel));

        removeButton.addActionListener(e -> removeQualification(qualificationsList, qualificationsModel));

        JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanelLeft.add(addButton);
        buttonPanelLeft.add(removeButton);

        JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = createButton("Next: Experience", new Color(0, 120, 215));
        buttonPanelRight.add(nextButton);

        JPanel navigationButtonPanel = new JPanel(new BorderLayout());
        navigationButtonPanel.add(buttonPanelLeft, BorderLayout.WEST);
        navigationButtonPanel.add(buttonPanelRight, BorderLayout.EAST);
        navigationButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(navigationButtonPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> cardLayout.show(cardPanel, "ExperienceSection"));
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void addQualification(DefaultListModel<String> qualificationsModel) {
        String newQualification = JOptionPane.showInputDialog(this, "Enter new skill:");
        if (newQualification != null && !newQualification.trim().isEmpty()) {
            qualificationsModel.addElement(newQualification);
            dataModel.highlightsInfo.addQualification(newQualification);
        }
    }

    private void removeQualification(JList<String> qualificationsList, DefaultListModel<String> qualificationsModel) {
        int selectedIndex = qualificationsList.getSelectedIndex();
        if (selectedIndex != -1) {
            String removedQualification = qualificationsModel.getElementAt(selectedIndex);
            qualificationsModel.remove(selectedIndex);
            dataModel.highlightsInfo.removeQualification(removedQualification);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a skill to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}

final class ExperienceInformation {
    public final List<Experience> experienceList = new ArrayList<>();

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void addExperience(String jobTitle, String company, String jobDescription, String startDate, String endDate) {
        experienceList.add(new Experience(jobTitle, company, jobDescription, startDate, endDate));
    }
}

final class Experience {
    private final String jobTitle;
    private final String company;
    private final String jobDescription;
    private final String startDate;
    private final String endDate;

    public Experience(String jobTitle, String company, String jobDescription, String startDate, String endDate) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.jobDescription = jobDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Job Title: " + jobTitle +"\n" + " Company: " + company +"\n" + " Job Description: "+"\n" + jobDescription +"\n"+
                " Start Date: " + startDate +"\n" + " End Date: " + endDate +"\n";
    }
}

final class ExperienceSection extends JPanel {
    private final UserDataModel dataModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JPanel experiencePanel;
    private JTextField jobTitleField;
    private JTextField companyField;
    private JTextArea jobDescriptionField;
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private int experiencePanels = 0;

    public ExperienceSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.dataModel = dataModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel sectionLabel = new JLabel("Experience Section");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sectionLabel.setForeground(new Color(0, 120, 215));
        sectionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sectionLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(sectionLabel, BorderLayout.NORTH);

        experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(experiencePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Experience");
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanelLeft.add(addButton);

        JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next: Education");
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        buttonPanelRight.add(nextButton);

        JPanel navigationButtonPanel = new JPanel(new BorderLayout());
        navigationButtonPanel.add(buttonPanelLeft, BorderLayout.WEST);
        navigationButtonPanel.add(buttonPanelRight, BorderLayout.EAST);
        navigationButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(navigationButtonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            if (experiencePanels > 0 && !jobTitleField.getText().isEmpty() && !companyField.getText().isEmpty() && !jobDescriptionField.getText().isEmpty()) {
                dataModel.experiencesInfo.addExperience(jobTitleField.getText(), companyField.getText(), jobDescriptionField.getText(), startSpinner.getValue().toString(), endSpinner.getValue().toString());
            }
            addExperiencePanel();
            experiencePanels++;
        });

        nextButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "EducationSection");
            if (experiencePanels > 0 && !jobTitleField.getText().isEmpty() && !companyField.getText().isEmpty() && !jobDescriptionField.getText().isEmpty()) {
                dataModel.experiencesInfo.addExperience(jobTitleField.getText(), companyField.getText(), jobDescriptionField.getText(), startSpinner.getValue().toString(), endSpinner.getValue().toString());
            }
        });
    }

    private void addExperiencePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        JPanel entryFieldsPanel = new JPanel(new GridBagLayout());
        entryFieldsPanel.setBackground(Color.WHITE);
        entryFieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel jobTitleLabel = new JLabel("Job Title:");
        entryFieldsPanel.add(jobTitleLabel, gbc);

        gbc.gridx++;
        jobTitleField = new JTextField(30);
        entryFieldsPanel.add(jobTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel companyLabel = new JLabel("Company:");
        entryFieldsPanel.add(companyLabel, gbc);

        gbc.gridx++;
        companyField = new JTextField(30);
        entryFieldsPanel.add(companyField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel jobDescriptionLabel = new JLabel("Job Description:");
        entryFieldsPanel.add(jobDescriptionLabel, gbc);

        gbc.gridx++;
        jobDescriptionField = new JTextArea(10,30);
        jobDescriptionField.setLineWrap(true); // Enable line wrap
        jobDescriptionField.setWrapStyleWord(true); // Wrap at word boundaries
        entryFieldsPanel.add(new JScrollPane(jobDescriptionField), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel startDateLabel = new JLabel("Start Date:");
        entryFieldsPanel.add(startDateLabel, gbc);

        gbc.gridx++;
        startSpinner = createDateSpinner();
        entryFieldsPanel.add(startSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel endDateLabel = new JLabel("End Date:");
        entryFieldsPanel.add(endDateLabel, gbc);

        gbc.gridx++;
        endSpinner = createDateSpinner();
        entryFieldsPanel.add(endSpinner, gbc);

        panel.add(entryFieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeButton = new JButton("Remove Experience");
        removeButton.setBackground(new Color(255, 0, 0));
        removeButton.setForeground(Color.WHITE);

        removeButton.addActionListener(e -> {
            experiencePanel.remove(panel);
            experiencePanel.revalidate();
            experiencePanel.repaint();
            dataModel.experiencesInfo.experienceList.removeIf(info -> info.getJobTitle().equals(jobTitleField.getText()) && info.getCompany().equals(companyField.getText()) && info.getJobDescription().equals(jobDescriptionField.getText()));
            experiencePanels--;
        });
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        experiencePanel.add(panel);
        experiencePanel.revalidate();
        experiencePanel.repaint();
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MM/dd/yyyy");
        spinner.setEditor(editor);
        return spinner;
    }
}

final class EducationInformation {
    private final List<TertiaryEducation> tertiaryEducationList = new ArrayList<>();
    private String secondarySchool;
    private String kcseGrade;
    private String secondaryCompletionYear;
    private String primarySchool;
    private String kcpeMarks;
    private String primaryCompletionYear;

    public List<TertiaryEducation> getTertiaryEducationList() {
        return tertiaryEducationList;
    }

    public void addTertiaryEducation(String institution, String course, String graduationDate) {
        tertiaryEducationList.add(new TertiaryEducation(institution, course, graduationDate));
    }

    public String getSecondarySchool() {
        return secondarySchool;
    }

    public void setSecondarySchool(String secondarySchool) {
        this.secondarySchool = secondarySchool;
    }

    public String getKcseGrade() {
        return kcseGrade;
    }

    public void setKcseGrade(String kcseGrade) {
        this.kcseGrade = kcseGrade;
    }

    public String getSecondaryCompletionYear() {
        return secondaryCompletionYear;
    }

    public void setSecondaryCompletionYear(String secondaryCompletionYear) {
        this.secondaryCompletionYear = secondaryCompletionYear;
    }

    public String getPrimarySchool() {
        return primarySchool;
    }

    public void setPrimarySchool(String primarySchool) {
        this.primarySchool = primarySchool;
    }

    public String getKcpeMarks() {
        return kcpeMarks;
    }

    public void setKcpeMarks(String kcpeMarks) {
        this.kcpeMarks = kcpeMarks;
    }

    public String getPrimaryCompletionYear() {
        return primaryCompletionYear;
    }

    public void setPrimaryCompletionYear(String primaryCompletionYear) {
        this.primaryCompletionYear = primaryCompletionYear;
    }
}

final class TertiaryEducation {
    private final String institution;
    private final String course;
    private final String graduationDate;

    public TertiaryEducation(String institution, String course, String graduationDate) {
        this.institution = institution;
        this.course = course;
        this.graduationDate = graduationDate;
    }

    public String getInstitution() {
        return institution;
    }

    public String getCourse() {
        return course;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    @Override
    public String toString() {
        return "Institution: " + getInstitution() +"\n"+ " Course: " + getCourse() +"\n"+ " Graduation Date: " + getGraduationDate() +"\n";
    }
}

final class EducationSection extends JPanel {
    private UserDataModel dataModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextField uniField;
    private JTextField courseField;
    private SpinnerDateModel gradDateModel;

    private JTextField primarySchoolField;
    private JTextField kcpeMarksField;
    private JComboBox<String> primaryCompletionYearField;
    private JTextField secondarySchoolField;
    private JComboBox<String> kcseGradeComboBox;
    private JComboBox<String> secondaryCompletionYearField;
    private int eduPanels = 1;

    private final String[] kcseGrades = {"A", "A-", "B+", "B", "B-", "C+", "C","C-", "D+", "D", "D-","E"};


    public EducationSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.dataModel = dataModel;

        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(240, 240, 240));

        addPrimarySecondaryFields(leftPanel);
        add(leftPanel);

        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(new Color(240, 240, 240));

        addTertiaryEducationField(rightPanel);

        JButton addTertiaryButton = new JButton("Add Another Tertiary Education");
        addTertiaryButton.setBackground(new Color(0, 120, 215));
        addTertiaryButton.setForeground(Color.WHITE);
        addTertiaryButton.addActionListener(e -> {
            if (eduPanels > 0 && validateTertiaryFields()) {
                dataModel.educationInfo.addTertiaryEducation(
                        uniField.getText(),
                        courseField.getText(),
                        gradDateModel.getDate().toString()
                );
                addTertiaryEducationField(rightPanel);
                revalidate();
                repaint();
                eduPanels++;
            } else {
                JOptionPane.showMessageDialog(null, "Please fill out all fields for the current tertiary education.");
            }
        });

        JPanel tertiaryButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tertiaryButtonPanel.add(addTertiaryButton);
        rightPanel.add(tertiaryButtonPanel);

        add(rightPanel);

        JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next: Referees");
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        buttonPanelRight.add(nextButton);

        JPanel navigationButtonPanel = new JPanel(new BorderLayout());
        navigationButtonPanel.add(buttonPanelRight, BorderLayout.SOUTH);
        navigationButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(navigationButtonPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateTertiaryFields()) {
                    dataModel.educationInfo.addTertiaryEducation(
                            uniField.getText(),
                            courseField.getText(),
                            gradDateModel.getDate().toString()
                    );

                    String kcpeMarks = kcpeMarksField.getText();
                    int marks = Integer.parseInt(kcpeMarks);
                    if(marks >= 0 && marks <= 500){
                        dataModel.educationInfo.setKcpeMarks(kcpeMarksField.getText());
                        dataModel.educationInfo.setPrimarySchool(primarySchoolField.getText());
                        dataModel.educationInfo.setPrimaryCompletionYear((String)primaryCompletionYearField.getSelectedItem());
                        dataModel.educationInfo.setSecondarySchool(secondarySchoolField.getText());
                        dataModel.educationInfo.setKcseGrade(kcseGradeComboBox.getSelectedItem().toString());
                        dataModel.educationInfo.setSecondaryCompletionYear((String)secondaryCompletionYearField.getSelectedItem());
                        cardLayout.show(cardPanel, "RefereeSection");
                    }else{
                        JOptionPane.showMessageDialog(null, "Please enter valid KCPE marks.");
                    }
                }
            }
        });
    }

    private boolean validateTertiaryFields() {
        return !uniField.getText().isEmpty() &&
                !courseField.getText().isEmpty() &&
                gradDateModel.getValue() != null;
    }

    private void addPrimarySecondaryFields(JPanel panel) {
        JLabel primaryHeadingLabel = new JLabel("Basic Education");
        primaryHeadingLabel.setForeground(new Color(0, 120, 215));
        Font font = primaryHeadingLabel.getFont();
        primaryHeadingLabel.setFont(new Font(font.getName(), Font.BOLD, 16));
        primaryHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(primaryHeadingLabel);

        primarySchoolField = new JTextField();
        primarySchoolField.setToolTipText("Enter primary school name");

        kcpeMarksField = new JTextField();
        kcpeMarksField.setToolTipText("Enter your KCPE marks");

        primaryCompletionYearField = new JComboBox<>();
        primaryCompletionYearField.setToolTipText("Enter primary completion year");
        for(int year = 1900; year<=2100;year++){
            primaryCompletionYearField.addItem(String.valueOf(year));
        }

        secondarySchoolField = new JTextField();
        secondarySchoolField.setToolTipText("Enter secondary school name");

        kcseGradeComboBox = new JComboBox<>(kcseGrades);

        secondaryCompletionYearField = new JComboBox<>();
        secondaryCompletionYearField.setToolTipText("Enter secondary completion year");
        for(int year = 1900; year<=2100;year++){
            secondaryCompletionYearField.addItem(String.valueOf(year));
        }

        panel.add(new JLabel("Primary School:"));
        panel.add(primarySchoolField);
        panel.add(new JLabel("KCPE Marks:"));
        panel.add(kcpeMarksField);
        panel.add(new JLabel("Primary Completion Year:"));
        panel.add(primaryCompletionYearField);
        panel.add(new JLabel("Secondary School:"));
        panel.add(secondarySchoolField);
        panel.add(new JLabel("KCSE Grade:"));
        panel.add(kcseGradeComboBox);
        panel.add(new JLabel("Secondary Completion Year:"));
        panel.add(secondaryCompletionYearField);
    }

    private void addTertiaryEducationField(JPanel panel) {
        JLabel tertiaryHeadingLabel = new JLabel("Tertiary Education");
        tertiaryHeadingLabel.setForeground(new Color(0, 120, 215));
        Font font = tertiaryHeadingLabel.getFont();
        tertiaryHeadingLabel.setFont(new Font(font.getName(), Font.BOLD, 16));
        tertiaryHeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(tertiaryHeadingLabel);

        uniField = new JTextField();
        uniField.setToolTipText("Enter tertiary institution name");

        courseField = new JTextField();
        courseField.setToolTipText("Enter course name");

        gradDateModel = new SpinnerDateModel();
        JSpinner gradDateSpinner = new JSpinner(gradDateModel);
        gradDateSpinner.setEditor(new JSpinner.DateEditor(gradDateSpinner, "dd/MM/yyyy"));

        panel.add(new JLabel("Tertiary Institution:"));
        panel.add(uniField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);
        panel.add(new JLabel("Graduation Date:"));
        panel.add(gradDateSpinner);
    }
}

final class RefereeInformation {
    private List<Referee> refereeList = new ArrayList<>();

    public List<Referee> getRefereeList() {
        return refereeList;
    }

    public void addReferee(String name, String jobTitle, String email, String phone) {
        refereeList.add(new Referee(name, jobTitle, email, phone));
    }
}

final class Referee {
    private final String name;
    private final String jobTitle;
    private final String email;
    private final String phone;

    public Referee(String name, String jobTitle, String email, String phone) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Name: " + name +"\n"+ " Job Title: " + jobTitle +"\n"+ " Email: " + email +"\n"+ " Phone: " + phone +"\n";
    }
}

final class RefereeSection extends JPanel {
    private final List<RefereePanel> refereePanels = new ArrayList<>();
    private final int MIN_REFEREE_COUNT = 3;
    private final UserDataModel dataModel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public RefereeSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        this.dataModel = dataModel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(207, 219, 232), 5));

        JLabel sectionLabel = new JLabel("Referees Section");
        sectionLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        sectionLabel.setForeground(new Color(0, 120, 215));
        sectionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sectionLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(sectionLabel, BorderLayout.NORTH);

        JPanel refereePanel = new JPanel();
        refereePanel.setLayout(new BoxLayout(refereePanel, BoxLayout.Y_AXIS));
        refereePanel.setBackground(new Color(245, 245, 245));
        add(refereePanel, BorderLayout.CENTER);

        for (int i = 0; i < MIN_REFEREE_COUNT; i++) {
            RefereePanel panel = new RefereePanel();
            refereePanels.add(panel);
            refereePanel.add(panel);
        }

        JPanel navigationButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navigationButtonPanel.setBackground(Color.WHITE);

        JButton nextButton = createButton("Next: Save As", e -> {
            List<Integer> validContacts = new ArrayList<>();
            if (validateFields()) {
                for (RefereePanel panel : refereePanels) {
                    if(panel.validateContacts()){
                        dataModel.refereeInfo.addReferee(panel.getNameField().getText(),
                                panel.getJobTitleField().getText(),
                                panel.getEmailField().getText(),
                                panel.getPhoneNumberField().getText());
                        validContacts.add(1);
                    }else{
                        JOptionPane.showMessageDialog(null, "Please fill in the phone number and email address correctly.");
                        validContacts.add(0);
                    }
                }
                boolean allValid = true;
                for (Integer contact : validContacts) {
                    if (contact != 1) {
                        allValid = false;
                        break;
                    }
                }

                if (allValid) {
                    cardLayout.show(cardPanel, "SaveAsSection");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please provide details for all referees.");
            }

        });
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        navigationButtonPanel.add(nextButton);
        navigationButtonPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(navigationButtonPanel, BorderLayout.SOUTH);
    }

    private boolean validateFields() {
        return refereePanels.stream().allMatch(RefereePanel::isFilled);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(actionListener);
        return button;
    }

    private class RefereePanel extends JPanel {
        private final JTextField nameField = createTextField();
        private final JTextField jobTitleField = createTextField();
        private final JTextField phoneNumberField = createTextField();
        private final JTextField emailField = createTextField();

        public RefereePanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(new Color(207, 219, 232), 2));
            setBackground(Color.WHITE);

            JPanel fieldsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            fieldsPanel.add(createLabel("Name:"), gbc);
            gbc.gridx++;
            fieldsPanel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            fieldsPanel.add(createLabel("Job Title:"), gbc);
            gbc.gridx++;
            fieldsPanel.add(jobTitleField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            fieldsPanel.add(createLabel("Phone Number:"), gbc);
            gbc.gridx++;
            fieldsPanel.add(phoneNumberField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            fieldsPanel.add(createLabel("Email:"), gbc);
            gbc.gridx++;
            fieldsPanel.add(emailField, gbc);

            add(fieldsPanel, BorderLayout.CENTER);

            phoneNumberField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validatePhone();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validatePhone();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    validatePhone();
                }
            });

            emailField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validateEmail();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validateEmail();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    validateEmail();
                }
            });
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Calibri", Font.BOLD, 14));
            label.setForeground(new Color(31, 73, 125));
            return label;
        }

        private JTextField createTextField() {
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(200, 25));
            return textField;
        }

        public boolean isFilled() {
            return !nameField.getText().isEmpty() && !jobTitleField.getText().isEmpty()
                    && !phoneNumberField.getText().isEmpty() && !emailField.getText().isEmpty();
        }

        public JTextField getNameField() {
            return nameField;
        }

        public JTextField getJobTitleField() {
            return jobTitleField;
        }

        public JTextField getPhoneNumberField() {
            return phoneNumberField;
        }

        public JTextField getEmailField() {
            return emailField;
        }

        private void validatePhone() {
            String phoneRegex = "^(07|01|\\+2547|\\+2541)\\d{8}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(phoneNumberField.getText());
            phoneNumberField.setForeground(matcher.matches() ? Color.BLACK : Color.RED);
        }

        private void validateEmail() {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(emailField.getText());
            emailField.setForeground(matcher.matches() ? Color.BLACK : Color.RED);
        }

        private boolean validateContacts() {
            return phoneNumberField.getForeground() != Color.RED &&
                    emailField.getForeground() != Color.RED;
        }
    }
}

final class SaveAsSection extends JPanel {
    public SaveAsSection(CardLayout cardLayout, JPanel cardPanel, UserDataModel dataModel) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel sectionLabel = new JLabel("Save As Section");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        sectionLabel.setForeground(new Color(0, 120, 215));
        sectionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sectionLabel.setBorder(new EmptyBorder(20, 20, 10, 20));
        add(sectionLabel, BorderLayout.NORTH);

        // Save Button
        JButton saveButton = new JButton("Save as PDF");
        saveButton.setBackground(new Color(0, 120, 215));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        saveButton.setPreferredSize(new Dimension(160, 50));
        saveButton.addActionListener(e -> saveResume(dataModel));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void saveResume(UserDataModel dataModel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Resume");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getAbsolutePath();
            if (!fileName.toLowerCase().endsWith(".pdf")) {
                fileName += ".pdf"; // Ensure the file has a .pdf extension
            }

            try {
                // Font paths
                final String Rubik = "C:\\Users\\User\\Desktop\\3rd Year Project\\version_5\\src\\main\\java\\Fonts\\Rubik-VariableFont_wght.ttf";
                final String Rubik_Medium = "C:\\Users\\User\\Desktop\\3rd Year Project\\version_5\\src\\main\\java\\Fonts\\Rubik-Medium.ttf";

                // PDF creation code...
                PdfWriter pdfWriter = new PdfWriter(fileName);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                Document document = new Document(pdfDocument);
                PdfFont objRubic = PdfFontFactory.createFont(Rubik, true);
                PdfFont objRubic_Medium = PdfFontFactory.createFont(Rubik_Medium, true);

                com.itextpdf.kernel.color.Color Primiry_color = new DeviceRgb(47, 79, 79);
                com.itextpdf.kernel.color.Color Secondy_color = new DeviceRgb(0, 128, 128);

                //MainHeading
                Style MainHeading = new Style();
                MainHeading
                        .setFontColor(Primiry_color)
                        .setBold()
                        .setFont(objRubic_Medium)
                        .setFontSize(10)
                        .setFont(objRubic)
                        .setTextAlignment(TextAlignment.LEFT);

                //SubHeading
                Style SubHeading = new Style();
                SubHeading.setFontSize(10)
                        .setFontColor(Secondy_color)
                        .setFont(objRubic)
                        .setBold();

                //SubText
                Style MainText = new Style();
                MainText.setFontSize(8)
                        .setFont(objRubic);

                //Resume header
                Paragraph headerParagraph = new Paragraph()
                        .setTextAlignment(TextAlignment.RIGHT);

                Text email_Head = new Text("Email Address:" + "\t" + dataModel.personalInfo.getEmail()).addStyle(MainText);
                Text phoneTxt = new Text("\n" + "Contact:" + "\t" + dataModel.personalInfo.getPhone()).addStyle(MainText);
                Text genderTxt = new Text("\n" + "Gender:" + "\t" + dataModel.personalInfo.getGender()).addStyle(MainText);
                headerParagraph
                        .add(email_Head)
                        .add(phoneTxt)
                        .add(genderTxt);

                document.add(headerParagraph);

                //Name section
                Paragraph fullNameParagraph = new Paragraph()
                        .setBackgroundColor(Primiry_color, 1000)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setHeight(30)
                        .setPadding(3)
                        .setMarginBottom(10);

                Text fullNameTxt = new Text(" " + dataModel.personalInfo.getFirstName() + " " + dataModel.personalInfo.getLastName())
                        .setFontSize(20f)
                        .setFontColor(com.itextpdf.kernel.color.Color.WHITE)
                        .setFont(objRubic_Medium);

                fullNameParagraph.add(fullNameTxt);
                document.add(fullNameParagraph);

                //Objectives
                float columnWidth[] = {200f, 200f, 200f, 200f};
                Table tblResume = new Table(columnWidth);
                tblResume.setPaddingTop(7f);
                tblResume.setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1));


                Paragraph objectivesParagraph = new Paragraph()
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setPaddingLeft(15f);
                Text objectiveTxt = new Text("\n" + dataModel.objectivesInfo.getObjectives())
                        .addStyle(MainText);

                objectivesParagraph
                        .add(objectiveTxt);

                tblResume.addCell(new Cell(0, 1)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1))
                        .add("OBJECTIVE")
                        .setPadding(7f)
                        .addStyle(MainHeading)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                tblResume.addCell(new Cell(0, 3)
                        .setPadding(7f)
                        .add(objectivesParagraph)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1)));

                //Education
                Paragraph educationParagraph = new Paragraph()
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setPaddingLeft(15f);

                for (TertiaryEducation tertiary : dataModel.educationInfo.getTertiaryEducationList()) {
                    Text txtTertiary = new Text("\n" + tertiary)
                            .addStyle(MainText);
                    educationParagraph
                            .add(txtTertiary);
                }
                Text txtSecondary = new Text("\n" + "Secondary School:" + "\t" + dataModel.educationInfo.getSecondarySchool())
                        .addStyle(MainText);
                Text txtKcseGrade = new Text("\n" + "Grade:" + "\t" + dataModel.educationInfo.getKcseGrade())
                        .addStyle(MainText);
                Text txtSecYearOfCompletion = new Text("\n" + "Year of Completion:" + "\t" + dataModel.educationInfo.getSecondaryCompletionYear())
                        .addStyle(MainText);
                Text txtPrimary = new Text("\n" + "Primary School:" + "\t" + dataModel.educationInfo.getPrimarySchool())
                        .addStyle(MainText);
                Text txtKcpeMarks = new Text("\n" + "Marks:" + "\t" + dataModel.educationInfo.getKcpeMarks())
                        .addStyle(MainText);
                Text txtPriTearOfCompletion = new Text("\n" + "Year of Completion:" + "\t" + dataModel.educationInfo.getPrimaryCompletionYear())
                        .addStyle(MainText);

                educationParagraph
                        .add(txtSecondary)
                        .add(txtKcseGrade)
                        .add(txtSecYearOfCompletion)
                        .add(txtPrimary)
                        .add(txtKcpeMarks)
                        .add(txtPriTearOfCompletion);

                tblResume.addCell(new Cell(0, 1)
                        .setPadding(7f)
                        .add("EDUCATION")
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .addStyle(MainHeading)
                );
                tblResume.addCell(new Cell(0, 3)
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1))
                        .add(educationParagraph));

                //Experience
                Paragraph experienceParagraph = new Paragraph()
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setPaddingLeft(15f);

                for (Experience experience : dataModel.experiencesInfo.getExperienceList()) {
                    Text txtExperience = new Text("\n" + experience)
                            .addStyle(MainText);
                    experienceParagraph.add(txtExperience);
                }

                tblResume.addCell(new Cell(0, 1)
                        .add("EXPERIENCE")
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .addStyle(MainHeading)
                );
                tblResume.addCell(new Cell(0, 3)
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderBottom(new SolidBorder(Primiry_color, 1))
                        .add(experienceParagraph));

                //Skills and Referees
                Paragraph skillsParagraph = new Paragraph()
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setPaddingLeft(15f);

                for (String skill : dataModel.highlightsInfo.getQualificationsList()) {
                    Text txtSkill = new Text("\n" + skill)
                            .addStyle(MainText);
                    skillsParagraph.add(txtSkill);
                }

                Paragraph refereeParagraph = new Paragraph()
                        .setPaddingLeft(20);

                for (Referee referee : dataModel.refereeInfo.getRefereeList()) {
                    Text txtReferee = new Text("\n" + referee)
                            .addStyle(MainText);
                    refereeParagraph.add(txtReferee);
                }

                tblResume.addCell(new Cell(0, 1)
                        .add("SKILLS")
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .addStyle(MainHeading)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderTop(new SolidBorder(Primiry_color, 1))
                );
                tblResume.addCell(new Cell(0, 1)
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderTop(new SolidBorder(Primiry_color, 1))
                        .add(skillsParagraph));

                tblResume.addCell(new Cell(0, 1)
                        .add("REFEREES")
                        .setPadding(7f)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 2))
                        .setBorderTop(new SolidBorder(Primiry_color, 1))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .addStyle(MainHeading));

                tblResume.addCell(new Cell(0, 1)
                        .add(refereeParagraph)
                        .setBorder(new SolidBorder(com.itextpdf.kernel.color.Color.WHITE, 1))
                        .setBorderTop(new SolidBorder(Primiry_color, 1))
                        .setPadding(7f));

                document.add(tblResume);

                document.close();

                // Display success message with absolute path
                JOptionPane.showMessageDialog(null, "Resume saved successfully at: \n" + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                // Display error message
                JOptionPane.showMessageDialog(null, "Failed to save resume!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
