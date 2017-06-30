package com.sai.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	
	@Override
	@GetMapping("/clone")
	public String clone() {
		File localPath;
		try {
			localPath = File.createTempFile("TestGitRepository", "");
			if (!localPath.delete()) {
				throw new IOException("Could not delete temporary file " + localPath);
			}
			// create the directory
			Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
			repository.create();
			System.out.println("Temporary repository at " + repository.getDirectory());
			File myfile = new File(repository.getDirectory().getParent(), "testfile");
			if (!myfile.createNewFile()) {
				throw new IOException("Could not create file " + myfile);
			}

			// run the add-call

			Git git = new Git(repository);
			git.add().addFilepattern("testfile").call();
			System.out.println("Added file to Temporary repository at " + repository.getDirectory());

			// and then commit the changes
			git.commit().setMessage("Added testfile").call();
			System.out.println("Commited file to Temporary repository at " + repository.getDirectory());

			System.out.println("Added file " + myfile + " to repository at " + repository.getDirectory());
			Git.lsRemoteRepository();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Done";

	}
	
}
