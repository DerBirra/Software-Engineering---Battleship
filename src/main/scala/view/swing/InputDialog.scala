package view.swing

import javax.swing._
import java.awt._

object InputDialog {

  def showDialog(title: String): Option[(Int, String, String)] = {

 val dialog = new JDialog()
    dialog.setTitle(title)
    dialog.setModal(true)
    dialog.setSize(400, 300)
    dialog.setLayout(new GridBagLayout())
    
    val constraints = new GridBagConstraints()
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.gridx = 0
    constraints.gridy = 0
    constraints.insets = new Insets(5, 5, 5, 5)

    val numberLabel = new JLabel("Bitte Spielfeld Größe angeben")
    val numberField = new JTextField(10)
    val name1Label = new JLabel("Bitte Name für Spieler 1 angeben")
    val name1Field = new JTextField(10)
    val name2Label = new JLabel("Bitte Name für Spieler 2 angeben")
    val name2Field = new JTextField(10)
    
    // Add number label and field
    constraints.gridx = 0
    constraints.gridy = 0
    dialog.add(numberLabel, constraints)
    constraints.gridx = 0
    constraints.gridy = 1
    dialog.add(numberField, constraints)

    // Add first name label and field
    constraints.gridx = 0
    constraints.gridy = 2
    dialog.add(name1Label, constraints)
    constraints.gridx = 0
    constraints.gridy = 3
    dialog.add(name1Field, constraints)

    // Add second name label and field
    constraints.gridx = 0
    constraints.gridy = 4
    dialog.add(name2Label, constraints)
    constraints.gridx = 0
    constraints.gridy = 5
    dialog.add(name2Field, constraints)

    val buttonPanel = new JPanel()
    val okButton = new JButton("Spiel starten")
    val cancelButton = new JButton("Abbrechen")

    buttonPanel.add(okButton)
    buttonPanel.add(cancelButton)

    constraints.gridx = 0
    constraints.gridy = 6
    constraints.gridwidth = 2
    dialog.add(buttonPanel, constraints)

    var result: Option[(Int, String, String)] = None

    okButton.addActionListener(_ => {
      try {
        val number = numberField.getText.toInt
        val name1 = name1Field.getText
        val name2 = name2Field.getText
        result = Some((number, name1, name2))
        dialog.dispose()
      } catch {
        case _: NumberFormatException =>
          JOptionPane.showMessageDialog(dialog, "Bitte eine ganze Zahl angeben")
      }
    })

    cancelButton.addActionListener(_ => {
      dialog.dispose()
    })

    dialog.setLocationRelativeTo(null)
    dialog.setVisible(true)
    result
  }
}