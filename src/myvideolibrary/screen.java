/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary;

import java.util.ArrayList;
import java.util.List;
import myvideolibrary.db.crud.FilmsCRUD;
import myvideolibrary.db.crud.GenresCRUD;
import myvideolibrary.db.crud.RealisateursCRUD;
import myvideolibrary.db.entity.Film;
import myvideolibrary.db.entity.Genre;
import myvideolibrary.db.entity.JPA.GenreJpaController;
import myvideolibrary.db.entity.Realisateur;
import org.infinispan.transaction.tm.DummyTransactionManager;
import org.infinispan.transaction.tm.DummyUserTransaction;

/**
 *
 * @author thebigbang
 */
public class screen extends javax.swing.JFrame {

    /**
     * Creates new form screen
     */
    public screen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FilmsButton = new javax.swing.JButton();
        GenresButton = new javax.swing.JButton();
        RealisateursButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListOfData = new javax.swing.JList();
        TypeDataLabel = new javax.swing.JLabel();
        GENERATE = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        FilmsButton.setText("Films");
        FilmsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilmsButtonActionPerformed(evt);
            }
        });

        GenresButton.setText("Genres");
        GenresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenresButtonActionPerformed(evt);
            }
        });

        RealisateursButton.setText("Réalisateurs");
        RealisateursButton.setActionCommand("");
        RealisateursButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RealisateursButtonActionPerformed(evt);
            }
        });

        ListOfData.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(ListOfData);

        TypeDataLabel.setText("#TypeData#");

        GENERATE.setText("GENERATE");
        GENERATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GENERATEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FilmsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TypeDataLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GenresButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RealisateursButton)))
                .addContainerGap(157, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GENERATE)
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FilmsButton)
                            .addComponent(GenresButton)
                            .addComponent(RealisateursButton))
                        .addGap(24, 24, 24)
                        .addComponent(TypeDataLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(GENERATE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenresButtonActionPerformed
        // TODO add your handling code here:
        TypeDataLabel.setText("Liste des Genres");
        ListOfData.removeAll();
        for (Genre genre : new GenresCRUD().getAll()) {
            ListOfData.add(this, genre);
        }
    }//GEN-LAST:event_GenresButtonActionPerformed

    private void FilmsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilmsButtonActionPerformed
        // TODO add your handling code here:
        TypeDataLabel.setText("Liste des Films");
                ListOfData.removeAll();
        for (Film genre : new FilmsCRUD().getAll()) {
            ListOfData.add(this, genre);
        }
    }//GEN-LAST:event_FilmsButtonActionPerformed

    private void RealisateursButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RealisateursButtonActionPerformed
        // TODO add your handling code here:
        TypeDataLabel.setText("Liste des Films");
                ListOfData.removeAll();
        for (Realisateur genre : new RealisateursCRUD().getAll()) {
            ListOfData.add(this, genre);
        }
    }//GEN-LAST:event_RealisateursButtonActionPerformed

    private void GENERATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GENERATEActionPerformed
        // TODO add your handling code here:
        Genre ungenre=new Genre();
        ungenre.setNom("action");
        new GenresCRUD().create(ungenre);
        
        Realisateur real=new Realisateur();
        real.setBiography("lorem ipsum dolor sit amet on da cliff");
        real.setName("jean hyve");
        real.setSurname("zorovtowitch");
        new RealisateursCRUD().create(real);
        
        Film monfilm1=new Film();
        monfilm1.setTitre("super film");
        monfilm1.setDescription("ce film est un film descriptuon :p");
        monfilm1.setNumerotation();
        List<Genre> filmGender=new ArrayList<>();
        filmGender.add(ungenre);
        List<Realisateur> filmReal=new ArrayList<>();
        filmReal.add(real);
        monfilm1.setRealisateurList(filmReal);
        monfilm1.setGenreList(filmGender);

        
        new FilmsCRUD().Create(monfilm1);
    }//GEN-LAST:event_GENERATEActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new screen().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FilmsButton;
    private javax.swing.JButton GENERATE;
    private javax.swing.JButton GenresButton;
    private javax.swing.JList ListOfData;
    private javax.swing.JButton RealisateursButton;
    private javax.swing.JLabel TypeDataLabel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
